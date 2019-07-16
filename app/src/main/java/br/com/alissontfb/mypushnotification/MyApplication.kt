package br.com.alissontfb.mypushnotification

import android.app.Application
import android.util.Log
import android.widget.Toast
import br.com.alissontfb.mypushnotification.data.realm.Migration
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initPushNotification()
        initRealm()
    }

    private fun initPushNotification() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                val token = task.result?.token

                Log.d("TOKEN", token)
            })

        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    private fun initRealm() {

        try {
            Realm.init(this)
            val realmConfiguration = RealmConfiguration.Builder()
                .name("myPushNotification.realm")
                .schemaVersion(0)
                .migration(Migration())
                .build()
            Realm.setDefaultConfiguration(realmConfiguration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}