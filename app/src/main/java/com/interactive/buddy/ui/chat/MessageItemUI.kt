package com.interactive.buddy.ui.chat

class MessageItemUi(val content:String,val textColor:Int,val messageType:Int){
    companion object {
        const val TYPE_PRIMARY_MESSAGE = 0
        const val TYPE_SECONDARY_MESSAGE = 1
    }
}