package com.interactive.buddy.ui.chatList.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.buddy.data.model.Mood
import com.interactive.buddy.services.ChatService
import com.interactive.buddy.ui.chatList.ChatItemUi
import com.interactive.buddy.ui.chatList.ChatItemUi.Companion.TYPE_NORMAL_CHAT

class ChatListViewModel : ViewModel() {
    val chats: MutableLiveData<List<ChatItemUi>> =  MutableLiveData();
    var chatService: ChatService? = null;

    fun getChats(): LiveData<List<ChatItemUi>> {
        return chats
    }

    fun loadChats() {
        // Fetch requests.
        chatService!!.getChats({ ch ->
            val temp: MutableList<ChatItemUi> = mutableListOf()
            ch.forEach(){
                var mood: Mood = Mood.HAPPY
                when (it.mood) {
                    1 -> mood = Mood.SAD
                    2 -> mood = Mood.OK
                    3 -> mood = Mood.HAPPY
                }
                temp.add(ChatItemUi(username = it.username, mood = mood,_id = it._id,it.lastMessage, TYPE_NORMAL_CHAT))
            }
            chats.postValue(temp);
        }, { })
    }

    fun setService(chatService: ChatService) {
        this.chatService = chatService
        loadChats()
    }
}