package com.interactive.buddy.data.model

class Message constructor (
    val _id: String,
    val chatId: String,
    val senderId: String,
    val username : String,
    private val content: String) {

    fun getMessageContent(): String{
        return content
    }
}