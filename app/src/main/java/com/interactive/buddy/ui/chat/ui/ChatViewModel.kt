package com.interactive.buddy.ui.chat.ui

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.buddy.ui.chat.MessageItemUi

class ChatViewModel : ViewModel() {
    private val messages: MutableLiveData<MutableList<MessageItemUi>> by lazy {
        MutableLiveData<MutableList<MessageItemUi>>().also {
            loadMessages()
        }
    }

    fun getMessages(): LiveData<MutableList<MessageItemUi>> {
        return messages
    }

    private fun loadMessages() {
        messages.value = arrayListOf(MessageItemUi(
            "test123",
            Color.WHITE,
            MessageItemUi.TYPE_SECONDARY_MESSAGE))
    }
}