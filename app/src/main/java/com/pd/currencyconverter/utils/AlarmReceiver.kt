package com.pd.currencyconverter.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pd.currencyconverter.R
import com.pd.currencyconverter.database.RoomAppDb
import com.pd.currencyconverter.dataclass.AlarmEntity
import java.util.*


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.e("TAG", "onReceive: ")
        if (intent.action.equals("com.pd.currencyconverter.alarm")) {
            val description: String = intent.extras!!.getString("description", "NO DESCRIPTION")
            val id: Int = intent.extras!!.getInt("id", 0)
            createNotificationChannel(context, description)
            notifyNotification(context, id, description)
        } else if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            Log.e("TAG", "onReceive: BOOT COMPLETE")
            setAlarmOnBoot(context)
        }
        Log.e("TAG", "onReceive: OVER")
    }

    private fun createNotificationChannel(context: Context, description: String) {
        Log.e("TAG", "createNotificationChannel: ")
        val alarmSound: Uri = Uri.parse(
            "android.resource://" + context.packageName.toString() + "/" + R.raw.alarm_sound
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                ConstantUtils.NOTIFICATION_CHANNEL_ID,
                "AlarmNotification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationChannel.description = description
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            notificationChannel.setSound(alarmSound, attributes)

            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(context: Context, id: Int, description: String) {

        val alarmSound: Uri = Uri.parse(
            "android.resource://" + context.packageName.toString() + "/" + R.raw.alarm_sound
        )

        with(NotificationManagerCompat.from(context)) {
            Log.e("TAG", "notifyNotification: ")
            val build = NotificationCompat.Builder(context, ConstantUtils.NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Alarm Notification")
                .setContentText(description)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_alarm)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notify(id, build.build())
        }

        val alarmDao = RoomAppDb.getAppDatabase((context))?.databaseDao()
        alarmDao?.deleteAlarm(id)
    }

    private fun setAlarmOnBoot(context: Context) {
        val alarmDao = RoomAppDb.getAppDatabase((context))?.databaseDao()
        var alarmData: List<AlarmEntity> = alarmDao?.getAllAlarms()!!
        alarmData.map {
            lateinit var calendar: Calendar
            calendar.timeInMillis = it.timestamp
            calendar.set(Calendar.SECOND, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra("description", it.description)
            intent.putExtra("id", it.id)
            intent.action = "com.pd.currencyconverter.alarm"
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                it.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            Log.e("TAG", "ALARM SET ${it.id}")
            return@map true
        }
    }

}
