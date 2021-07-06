package com.interactive.buddy.data

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.interactive.buddy.R
import com.interactive.buddy.services.SocketService

class App : Application() {
    private var CHANNEL_ID : String = "NEW_MESSAGE";
    companion object {
        var messageCollector: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
        var currentChatId: MutableLiveData<String> = MutableLiveData<String>("none")
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel();
        if(SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(this, SocketService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, intent)
            } else {
                this.startService(intent)
            }
        }
    }

    fun loginDone(){
        val intent = Intent(this, SocketService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, intent)
        } else {
            this.startService(intent)
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.new_message)
            val descriptionText = getString(R.string.new_message)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}