package com.interactive.buddy.data.model

import java.sql.Blob

class Message constructor (val chatId: String,val fromId : String, private val content: Blob) {
    fun getMessageContent(): String{
        return String(content.getBytes(0, content.length().toInt()))
    }
}