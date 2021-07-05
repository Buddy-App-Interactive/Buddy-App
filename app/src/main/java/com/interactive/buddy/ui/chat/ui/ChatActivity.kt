package com.interactive.buddy.ui.chat.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.ui.chat.ChatAdapter
import com.interactive.buddy.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var viewModel: ChatViewModel
    private var chatAdapter = ChatAdapter(mutableListOf())
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val model: ChatViewModel by viewModels()
        viewModel = model
        viewModel.getMessages().observe(this, { messages ->
            chatAdapter.data = messages
        })

        val messageRecyclerView: RecyclerView = binding.messageRecyclerView
        messageRecyclerView.adapter = chatAdapter
    }
}