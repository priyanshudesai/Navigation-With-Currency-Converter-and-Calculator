package com.pd.currencyconverter.services

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.pd.currencyconverter.NavigationActivity
import com.pd.currencyconverter.R
import com.pd.currencyconverter.utils.ConstantUtils
import java.util.*
import kotlin.math.roundToInt


class TimerService() : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    private val timer = Timer()
    private val uniqueID = 71399

    //    private lateinit var build: NotificationCompat.Builder
    var manager: NotificationManager? = null
    var chan: NotificationChannel? = null
    var notificationBuilder: NotificationCompat.Builder? = null
    var notification: Notification? = null
    var TIME: Double = 0.0

    override fun onCreate() {
        super.onCreate()
        Log.e("Timer Service", "onCreate: ")
        val check = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getBoolean(ConstantUtils.KEY_TIMER_CHECK, false)
        if (!check) {
            stopSelf()
            Log.e("Timer Service", "onCreate: Stop Self ")
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startMyOwnForeground()
            else
                startForeground(uniqueID, Notification())

        }
//        val check = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("check", "off")
//
//        if (check.equals("off")) {
//            stopSelf()
//            Log.e("TAG", "stop self")
////           stopForeground(true)
//        } else {
//
//        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val check = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getBoolean(ConstantUtils.KEY_TIMER_CHECK, false)
        Log.e("Timer Service", "onStartCommand: ")
        if (check) {
            TIME = intent?.getDoubleExtra(TIME_EXTRA, 0.0) ?: 0.0
            timer.scheduleAtFixedRate(TimeTask(), 0, 1000)

            return START_STICKY
        } else {
            timer.cancel()
            return START_NOT_STICKY
        }
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

//    override fun onTaskRemoved(rootIntent: Intent?) {
//        timer.cancel()
//        val check = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//            .getBoolean(ConstantUtils.KEY_TIMER_CHECK, false)
//        if (check) {
//            val broadcastIntent = Intent()
//            broadcastIntent.action = "restartservice"
//            broadcastIntent.setClass(this, MyBroadcastReceiver::class.java)
//            broadcastIntent.putExtra("time", TIME)
//            this.sendBroadcast(broadcastIntent)
//            Log.e("Timer Service", "service onDestroy: restart" )
//        }else{
//            timer.cancel()
//            Log.e("Timer Service", "service onDestroy: Check OFF" )
//        }
//        super.onTaskRemoved(rootIntent)
//    }

    private inner class TimeTask : TimerTask() {
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            TIME++
            intent.putExtra(TIME_EXTRA, TIME)
            sendBroadcast(intent)

            setTime(getTimeStringFromDouble(TIME))
//            createNotificationChannel(applicationContext, getTimeStringFromDouble(time))
//            notifyNotification(applicationContext, uniqueID, getTimeStringFromDouble(time))
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {
        Log.e("Timer Service", "Foreground ")
        val contentView = RemoteViews(packageName, R.layout.layout_timer_notification)
//        contentView.setImageViewResource(R.id.image, R.mipmap.ic_t)
        contentView.setTextViewText(R.id.title, "Timer Service")
        contentView.setTextViewText(R.id.text, "Timer Will Start")
        val intent = Intent(this, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, uniqueID, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        chan = NotificationChannel(
            ConstantUtils.TIMER_NOTIFICATION_CHANNEL_ID,
            ConstantUtils.TIMER_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_NONE
        )
        chan!!.lightColor = Color.BLUE
        chan!!.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        assert(manager != null)
        manager!!.createNotificationChannel(chan!!)
        notificationBuilder =
            NotificationCompat.Builder(this, ConstantUtils.TIMER_NOTIFICATION_CHANNEL_ID)
        notification = notificationBuilder!!.setOngoing(false)
            .setContentTitle(ConstantUtils.TIMER_NOTIFICATION_CHANNEL_NAME)
            .setContentText("TIMER")
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setSmallIcon(R.drawable.ic_timer)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
            .setContent(getCustomDesign("Timer Service", "Timer Will Start"))
//            .setCustomBigContentView(contentView)
//            .setContent(contentView)
//            .setCustomContentView(contentView)
//            .setWhen(System.currentTimeMillis())
//            .setOnlyAlertOnce(true)
            .setChannelId(ConstantUtils.TIMER_NOTIFICATION_CHANNEL_ID)
            .build()

        notification!!.flags = Notification.FLAG_NO_CLEAR or Notification.FLAG_ONGOING_EVENT
        startForeground(uniqueID, notification)
    }

    fun setTime(time: String) {
        val contentView = RemoteViews(packageName, R.layout.layout_timer_notification)
//        contentView.setImageViewResource(R.id.image, R.mipmap.ic_t)
        contentView.setTextViewText(R.id.title, "Timer Service")
        contentView.setTextViewText(R.id.text, time)
        val intent = Intent(this, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, uniqueID, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        notification = notificationBuilder!!.setOngoing(false)
            .setContentTitle(ConstantUtils.TIMER_NOTIFICATION_CHANNEL_NAME)
            .setContentText(time)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setSmallIcon(com.pd.currencyconverter.R.drawable.ic_timer)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
            .setContent(getCustomDesign("Timer Service", time))
//            .setCustomBigContentView(contentView)
//            .setContent(contentView)
//            .setCustomContentView(contentView)
//            .setWhen(System.currentTimeMillis())
//            .setOnlyAlertOnce(true)
            .setChannelId(ConstantUtils.TIMER_NOTIFICATION_CHANNEL_ID)
            .build()

        notification!!.flags = Notification.FLAG_NO_CLEAR or Notification.FLAG_ONGOING_EVENT
        startForeground(uniqueID, notification)
    }

    private fun getCustomDesign(
        title: String,
        message: String
    ): RemoteViews {
        val remoteViews = RemoteViews(
            applicationContext.packageName,
            R.layout.layout_timer_notification
        )
        remoteViews.setTextViewText(R.id.title_timer_notification, title)
        remoteViews.setTextViewText(R.id.time_timer_notification, message)
//        remoteViews.setImageViewResource(
//            R.id.ic_timer,
//            R.drawable.gfg
//        )
        return remoteViews
    }

//    private fun createNotificationChannel(context: Context, time: String) {
//        Log.e("TimerService", "createNotificationChannel: ")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(
//                ConstantUtils.TIMER_NOTIFICATION_CHANNEL_ID,
//                ConstantUtils.TIMER_NOTIFICATION_CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_HIGH
//            )
//            notificationChannel.description = time
//
//            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
//        }
//    }
//
//    private fun notifyNotification(context: Context, id: Int, time: String) {
//        with(NotificationManagerCompat.from(context)) {
//            Log.e("TimerService", "notifyNotification: ")
//            build =
//                NotificationCompat.Builder(context, ConstantUtils.TIMER_NOTIFICATION_CHANNEL_ID)
//                    .setContentTitle(ConstantUtils.TIMER_NOTIFICATION_CHANNEL_NAME)
//                    .setContentText(time)
//                    .setWhen(System.currentTimeMillis())
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setAutoCancel(false)
//                    .setOngoing(true)
//                    .setSmallIcon(com.pd.currencyconverter.R.drawable.ic_timer)
//                    .setOnlyAlertOnce(true)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//
//            notify(id, build.build())
//        }
//    }

    companion object {
        const val TIMER_UPDATED = "timerUpdated"
        const val TIME_EXTRA = "timeExtra"

        fun getTimeStringFromDouble(time: Double): String {
            val resultInt = time.roundToInt()
            val hours = resultInt % 86400 / 3600
            val minutes = resultInt % 86400 % 3600 / 60
            val seconds = resultInt % 86400 % 3600 % 60

            return makeTimeString(hours, minutes, seconds)
        }

        private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
            String.format("%02d:%02d:%02d", hour, min, sec)

    }


}