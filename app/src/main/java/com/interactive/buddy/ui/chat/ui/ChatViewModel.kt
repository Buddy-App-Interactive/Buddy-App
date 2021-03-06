package com.interactive.buddy.ui.chat.ui

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.buddy.data.App
import com.interactive.buddy.services.MessageService
import com.interactive.buddy.ui.chat.MessageItemUi
import com.interactive.buddy.ui.chat.MessageItemUi.Companion.TYPE_PRIMARY_MESSAGE
import com.interactive.buddy.ui.chat.MessageItemUi.Companion.TYPE_SECONDARY_MESSAGE
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatViewModel : ViewModel() {
    val messages: MutableLiveData<List<MessageItemUi>> =  MutableLiveData();
    var messageService: MessageService? = null;
    lateinit var userId: String;
    lateinit var chatId: String;

    fun getMessages(): LiveData<List<MessageItemUi>> {
        return messages
    }

    public fun sendMessage(content: String){
        Log.d("message",content)
        messageService!!.sendMessage(this.chatId,content,{
            loadMessages()
        },{})
    }

    fun loadMessages() {
        messageService!!.getMessages(chatId, { msg ->
            val temp: MutableList<MessageItemUi> = mutableListOf()
            msg.forEach() {
                val date: LocalDateTime = it.getCreatedDate()
                if ((it.senderId==userId)) {
                    temp.add(MessageItemUi(
                        it.getMessageContent(),
                            Color.WHITE,
                            it.username,
                            date.format(DateTimeFormatter.ofPattern("HH:mm")),
                            TYPE_PRIMARY_MESSAGE))
                } else {
                    temp.add(MessageItemUi(
                        it.getMessageContent(),
                        Color.WHITE,
                        it.username,
                        date.format(DateTimeFormatter.ofPattern("HH:mm")),
                        TYPE_SECONDARY_MESSAGE))
                }
            }
            messages.postValue(temp);
        }, { })
    }

    fun init(messageService: MessageService, userId: String, chatId: String) {
        this.messageService = messageService
        this.userId = userId
        this.chatId = chatId
        loadMessages()
    }
}