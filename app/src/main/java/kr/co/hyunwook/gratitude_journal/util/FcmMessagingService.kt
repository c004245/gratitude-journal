package kr.co.hyunwook.gratitude_journal.util

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kr.co.hyunwook.gratitude_journal.MainActivity
import kr.co.hyunwook.gratitude_journal.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

@AndroidEntryPoint
class FcmMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {

        sendNotification(remoteMessage = message)
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        Log.d("HWO", "onNewToken -> $token")
        super.onNewToken(token)
    }
    private fun sendNotification(remoteMessage: RemoteMessage) {
        Log.d("HWO", "sendNotification -> ${remoteMessage.notification?.title} -- ${remoteMessage.notification?.body}")
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val channelId = getString(R.string.app_name)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_main_app)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
