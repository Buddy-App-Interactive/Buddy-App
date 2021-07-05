package com.interactive.buddy.ui.chatList.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.buddy.data.model.Mood
import com.interactive.buddy.services.ChatService
import com.interactive.buddy.ui.chatList.ChatItemUi
import com.interactive.buddy.ui.chatList.ChatItemUi.Companion.TYPE_NORMAL_CHAT
import com.interactive.buddy.ui.request.ListItems.YourRequestsListAdapter

class ChatListViewModel : ViewModel() {
    val chats: MutableLiveData<List<ChatItemUi>> =  MutableLiveData();
    var chatService: ChatService? = null;

    fun getChats(): LiveData<List<ChatItemUi>> {
        return chats
    }

    private fun loadChats() {
        // Fetch requests.
        chatService!!.getChats({ ch ->
            val temp: MutableList<ChatItemUi> = mutableListOf()
            ch.forEach(){
                //TODO: CHANGE THIS to correct types
                temp.add(ChatItemUi(username = it.username, mood = Mood.HAPPY,_id = it._id, TYPE_NORMAL_CHAT))
            }
            chats.postValue(temp);
        }, { })
    }

    fun setService(chatService: ChatService) {
        this.chatService = chatService
        loadChats()
    }
}