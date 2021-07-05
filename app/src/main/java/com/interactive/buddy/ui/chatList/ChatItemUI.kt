package com.interactive.buddy.ui.chatList

import com.interactive.buddy.data.model.Mood

class ChatItemUi(val username:String, val mood: Mood,val _id:String, val chatType:Int){
    companion object {
        const val TYPE_NORMAL_CHAT = 0
    }
}