package com.interactive.buddy.ui.chatList.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.services.ChatService
import com.interactive.buddy.ui.chat.ui.ChatActivity
import com.interactive.buddy.ui.chatList.ChatListAdapter
import com.interactive.buddy.ui.chatList.ChatListAdapter.OnItemClickListener
import com.interactive.buddy.ui.navigation.NavigationActivity


class ChatListFragment : Fragment() {
    private lateinit var viewModel: ChatListViewModel
    private var chatListAdapter: ChatListAdapter = ChatListAdapter()
    private lateinit var chatService: ChatService;
    private lateinit var fragment: Fragment;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatListViewModel::class.java)
        chatService = ChatService(requireContext());
        viewModel.setService(chatService)
        fragment = this;

        chatListAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(chatId: String) {
                val myIntent = Intent(fragment.requireContext(), ChatActivity::class.java)
                myIntent.putExtra("chatId",chatId)
                fragment.startActivity(myIntent)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_chat_list, container, false)
        val chatsRecyclerView: RecyclerView = view.findViewById(R.id.chatsRecyclerView)
        chatsRecyclerView.adapter = chatListAdapter
        chatsRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.chats.observe(viewLifecycleOwner, {
            it?.let {
                chatListAdapter.submitNewData(it)
            }
        })


        return view
    }
}