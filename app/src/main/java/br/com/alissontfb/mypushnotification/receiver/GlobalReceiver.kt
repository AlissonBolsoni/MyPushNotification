package br.com.alissontfb.mypushnotification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import br.com.alissontfb.mypushnotification.firebase.MyFirebaseMessagingService

class GlobalReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent == null) return

        if (intent.action == "br.com.alissontfb.mypushnotification.firebase.action.start"){
            ContextCompat.startForegroundService(
                context,
                Intent(context, MyFirebaseMessagingService::class.java)
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startService(Intent(context, MyFirebaseMessagingService::class.java))
            }
        }
    }
}