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

    override fun toString(): String {
        return "Message(_id='$_id', chatId='$chatId', senderId='$senderId', username='$username', content='$content')"
    }
}