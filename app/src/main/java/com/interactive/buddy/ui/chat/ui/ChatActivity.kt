package com.interactive.buddy.ui.chat.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.databinding.ActivityChatBinding
import com.interactive.buddy.services.MessageService
import com.interactive.buddy.ui.chat.ChatAdapter


class ChatActivity : AppCompatActivity() {
    private var chatAdapter: ChatAdapter = ChatAdapter()
    private lateinit var messageService: MessageService
    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        binding = ActivityChatBinding.inflate(layoutInflater)

        messageService = MessageService(this);
        val model: ChatViewModel by viewModels()
        viewModel = model
        viewModel.init(messageService, SharedPrefManager.getInstance(this).user.userId, intent.getStringExtra("chatId")!!)


        val messageRecyclerView: RecyclerView = findViewById<RecyclerView>(R.id.messageRecyclerView)
        messageRecyclerView.adapter = chatAdapter

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.messages.observe(this, {
            it?.let {
                chatAdapter.submitNewData(it)
            }
        })
        
    }

    fun onSendClick(view: View){
        viewModel.sendMessage(findViewById<EditText>(R.id.editTextMessage).text.toString())
    }
}