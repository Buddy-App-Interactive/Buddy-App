package com.interactive.buddy.data.model

class Chat constructor(
    val _id: String,
    val username: String,
    val requestId: String,
    val mood: Int,
    val lastMessage: Message
    ) {
}