package br.com.alissontfb.mypushnotification.firebase

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.alissontfb.mypushnotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val notification = remoteMessage?.notification
        if (notification != null)
            showNotification(notification)
    }

    private fun showNotification(notification: RemoteMessage.Notification) {
        val builder = NotificationCompat.Builder(this)
        builder.setContentTitle(notification.title)
        builder.setContentText(notification.body)
        builder.setSmallIcon(R.drawable.notification_icon_background)
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }

}