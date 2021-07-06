package com.interactive.buddy.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interactive.buddy.R
import com.interactive.buddy.data.App
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.data.model.Message
import com.interactive.buddy.ui.chat.ui.ChatActivity
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class SocketService : Service() {
    private lateinit var mSocket: Socket
    private var CHANNEL_ID : String = "NEW_MESSAGE";
    private var ctx: Context = this;

    override fun onCreate() {
        super.onCreate()

        val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        val notificationBuilder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, "TAG", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableVibration(true)
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                notificationChannel
            )
        } else {
            notificationBuilder = NotificationCompat.Builder(this)
        }
        notificationBuilder
            .setContentText("Buddy app is running in Background")
            .setAutoCancel(true)
            .setLargeIcon(icon)
            .setColor(Color.RED)
            .setSmallIcon(R.mipmap.ic_launcher)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "TAG",
                NotificationManager.IMPORTANCE_NONE
            )
            notificationManager.createNotificationChannel(channel)
            val notification: Notification = Notification.Builder(applicationContext, CHANNEL_ID).build()
            startForeground(1, notification)
        } else {
            // startForeground(1, notification);
        }
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            mSocket = IO.socket("http://192.168.0.106:8000").connect()
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

        mSocket.emit(
            "storeClientInfo",
            "{\"customId\":\"" +SharedPrefManager.getInstance(ctx).user.userId+ "\"}"
        )

        val options = IO.Options()
        options.reconnection = true
        options.forceNew = true



        mSocket.on("NEW_MESSAGE") { args ->
            if (args[0] != null) {

                val type = object : TypeToken<Message>() {}.type
                val message: Message = Gson().fromJson(args[0].toString(), type)
                App.messageCollector.postValue(true)
                Log.d(App.currentChatId.value,message.chatId)

                if(!App.currentChatId.value.equals(message.chatId) && SharedPrefManager.getInstance(ctx).isLoggedIn) {
                    val myIntent = Intent(this, ChatActivity::class.java)
                    myIntent.putExtra("chatId",message.chatId)
                    val pendingIntent = PendingIntent.getActivity(
                        this,
                        0,
                        myIntent,
                        Intent.FILL_IN_ACTION
                    )

                    var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_message_24)
                        .setContentTitle(message.username)
                        .setContentText(message.getMessageContent())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)

                    with(NotificationManagerCompat.from(this)) {
                        // notificationId is a unique int for each notification that you must define
                        notify(message._id.hashCode(), builder.build())
                    }
                }
            }
        };


            return super.onStartCommand(intent, flags, startId)
        }
}