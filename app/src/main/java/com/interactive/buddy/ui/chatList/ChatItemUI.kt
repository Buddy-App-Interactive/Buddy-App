package com.interactive.buddy.ui.chatList

import com.interactive.buddy.data.model.Message
import com.interactive.buddy.data.model.Mood

class ChatItemUi(val username:String, val mood: Mood, val _id:String, val lastMessage: Message, val chatType:Int){
    companion object {
        const val TYPE_NORMAL_CHAT = 0
    }
}