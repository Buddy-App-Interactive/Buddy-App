package com.interactive.buddy.ui.chat.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.data.App
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
        val chatId = intent.getStringExtra("chatId")!!
        val chatName = intent.getStringExtra("chatName")!!
        val model: ChatViewModel by viewModels()
        viewModel = model
        viewModel.init(messageService, SharedPrefManager.getInstance(this).user.userId, chatId)

        App.currentChatId.postValue(chatId);

        val messageRecyclerView: RecyclerView = findViewById(R.id.messageRecyclerView)
        val chatbar: TextView = findViewById(R.id.chatNameBar)
        chatbar.text = chatName

        messageRecyclerView.adapter = chatAdapter
        var layout = LinearLayoutManager(this)
        layout.stackFromEnd = true
        messageRecyclerView.layoutManager = layout

        viewModel.messages.observe(this, {
            it?.let {
                chatAdapter.submitNewData(it)
                messageRecyclerView.scrollToPosition(it.size - 1);
            }
        })

        App.messageCollector.observe(this, {
            it?.let {
                if(it) {
                    viewModel.loadMessages()
                    App.messageCollector.postValue(false)
                }
            }
        })
        
    }

    fun onSendClick(view: View){
        var tv: EditText = findViewById(R.id.editTextMessage)
        viewModel.sendMessage(tv.text.toString())
        tv.clearFocus()
        tv.text.clear()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun onBackClick(view: View){
        this.finish();
    }

    override fun onDestroy() {
        super.onDestroy()
        App.currentChatId.postValue("none")
    }
}