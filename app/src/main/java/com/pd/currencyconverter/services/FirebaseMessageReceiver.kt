package com.pd.currencyconverter.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pd.currencyconverter.NavigationActivity
import com.pd.currencyconverter.R
import com.pd.currencyconverter.utils.ConstantUtils
import java.net.URL


class FirebaseMessageReceiver : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            // Since the notification is received directly from
            // FCM, the title and the body can be fetched
            // directly as below.
            showNotification(
                remoteMessage.notification!!.title ?: "",
                remoteMessage.notification!!.body ?: "",
                remoteMessage.notification!!.imageUrl.toString()

            )
        }
    }

//    // Method to get the custom Design for the display of
//    // notification.
//    private fun getCustomDesign(
//        title: String,
//        message: String
//    ): RemoteViews {
//        val remoteViews = RemoteViews(
//            ApplicationProvider.getApplicationContext<Context>().getPackageName(),
//            R.layout.notification
//        )
//        remoteViews.setTextViewText(R.id.title, title)
//        remoteViews.setTextViewText(R.id.message, message)
//        remoteViews.setImageViewResource(
//            R.id.icon,
//            R.drawable.gfg
//        )
//        return remoteViews
//    }

    private fun showNotification(
        title: String,
        message: String,
        imageUrl: String
    ) {
        val intent = Intent(this, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        lateinit var url: URL
        lateinit var image: Bitmap
        try {
            url = URL(imageUrl)
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: Exception) {

        }

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            ConstantUtils.PUSH_NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_currency_converter)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setDefaults(Notification.DEFAULT_ALL)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(pendingIntent)
//            Log.e("TAG", imageUrl)
        if (imageUrl != "" && imageUrl != "null") {
            builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
        }


//        builder = if (Build.VERSION.SDK_INT
//            >= Build.VERSION_CODES.JELLY_BEAN
//        ) {
//            builder.setContent(
//                getCustomDesign(title, message)
//            )
//        }
//        else {
//            builder.setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.gfg)
//        }

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                ConstantUtils.PUSH_NOTIFICATION_CHANNEL_ID,
                ConstantUtils.PUSH_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager.notify(0, builder.build())
    }
}