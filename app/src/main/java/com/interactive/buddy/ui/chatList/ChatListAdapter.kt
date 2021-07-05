package com.interactive.buddy.ui.chatList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.ui.chatList.ChatItemUi.Companion.TYPE_NORMAL_CHAT

class ChatListAdapter() : RecyclerView.Adapter<ChatViewHolder<*>>() {
    val data = mutableListOf<ChatItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_NORMAL_CHAT -> {
                val view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
                NormalChatViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder<*>, position: Int) {
        val item = data[position]
        when (holder) {
            is NormalChatViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].chatType

    fun submitNewData(it: MutableList<ChatItemUi>) {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
    }

    class NormalChatViewHolder(val view: View) : ChatViewHolder<ChatItemUi>(view) {
        private val content = view.findViewById<TextView>(R.id.chat_username)

        override fun bind(item: ChatItemUi) {
            content.text = item.username
        }
    }
}