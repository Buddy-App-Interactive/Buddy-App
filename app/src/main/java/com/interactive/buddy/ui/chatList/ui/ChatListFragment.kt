package com.interactive.buddy.ui.chatList.ui

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
import com.interactive.buddy.ui.chatList.ChatListAdapter


class ChatListFragment : Fragment() {
    private lateinit var viewModel: ChatListViewModel
    private var chatListAdapter: ChatListAdapter = ChatListAdapter()
    private lateinit var chatService: ChatService;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatListViewModel::class.java)
        chatService = ChatService(requireContext());
        viewModel.setService(chatService)
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