package com.interactive.buddy.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Message constructor (
    val _id: String,
    val chatId: String,
    val senderId: String,
    val username : String,
    private val created : String,
    private val content: String) {

    fun getMessageContent(): String{
        try{
            return Gson().fromJson(content, String::class.java);
        }catch (ex: Exception){
            return "No messages sent yet.."
        }

    }

    override fun toString(): String {
        return "Message(_id='$_id', chatId='$chatId', senderId='$senderId', username='$username', created=$created, content='$content')"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCreatedDate(): LocalDateTime{
        return LocalDateTime.parse(created, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


}