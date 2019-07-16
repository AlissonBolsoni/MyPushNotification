package br.com.alissontfb.mypushnotification.firebase

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.alissontfb.mypushnotification.R
import br.com.alissontfb.mypushnotification.data.dao.TaskDao
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
//        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        remoteMessage?.notification?.let {
            val taskDescription = it.body
            if (taskDescription != null && taskDescription.isNotEmpty()) {
                TaskDao().createTask(taskDescription)
                showNotification(it)
            }
        }
    }

    private fun showNotification(notification: RemoteMessage.Notification) {
        val builder = NotificationCompat.Builder(this)
        builder.setContentTitle(notification.title)
        builder.setContentText(notification.body)
        builder.setSmallIcon(R.drawable.notification_icon_background)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }

    override fun onDestroy() {
        sendBroadcast(Intent("br.com.alissontfb.mypushnotification.firebase.action.start"))
        super.onDestroy()
    }
}