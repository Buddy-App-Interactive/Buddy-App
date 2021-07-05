package com.interactive.buddy.ui.chatList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.buddy.ui.chatList.ChatItemUi
import com.interactive.buddy.ui.chatList.ChatItemUi.Companion.TYPE_NORMAL_CHAT

class ChatListViewModel : ViewModel() {
    val chats: MutableLiveData<MutableList<ChatItemUi>> =  MutableLiveData();

    fun getChats(): LiveData<MutableList<ChatItemUi>> {
        loadChats()
        return chats
    }

    private fun loadChats() {
        chats.postValue(mutableListOf(ChatItemUi(
            "test123",
            TYPE_NORMAL_CHAT
        ),ChatItemUi(
            "aaaaa",
            TYPE_NORMAL_CHAT
        )))
    }
}