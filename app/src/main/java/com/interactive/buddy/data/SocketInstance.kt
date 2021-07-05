package com.interactive.buddy.data

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interactive.buddy.R
import com.interactive.buddy.data.model.Message
import java.net.URISyntaxException

class SocketInstance : Application() {
    private var mSocket: Socket? = null
    private var CHANNEL_ID : String = "NEW_MESSAGE";

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel();
        try {
            mSocket = IO.socket(URLs.URL_MESSAGE_SOCKET)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

        mSocket = this.getMSocket()
        mSocket?.connect()
        val options = IO.Options()
        options.reconnection = true
        options.forceNew = true

        mSocket?.on("NEW_MESSAGE") { args ->
            if (args[0] != null)
            {
                val type = object : TypeToken<Message>() {}.type
                val messages = Gson().fromJson<Message>(args[0] as String,type)

                var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_message_24)
                    .setContentTitle(messages.username)
                    .setContentText(messages.getMessageContent())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                with(NotificationManagerCompat.from(this)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(messages.chatId.hashCode(), builder.build())
                }
            }
        }
    }

    fun getMSocket(): Socket? {
        return mSocket
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