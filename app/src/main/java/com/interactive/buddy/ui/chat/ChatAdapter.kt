package com.interactive.buddy.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.ui.chat.MessageItemUi.Companion.TYPE_PRIMARY_MESSAGE
import com.interactive.buddy.ui.chat.MessageItemUi.Companion.TYPE_SECONDARY_MESSAGE
import com.interactive.buddy.ui.chatList.ChatItemUi

class ChatAdapter() : RecyclerView.Adapter<MessageViewHolder<*>>() {
    val data = mutableListOf<MessageItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_PRIMARY_MESSAGE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.chat_message_primary, parent, false)
                PrimaryMessageViewHolder(view)
            }
            TYPE_SECONDARY_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_message_secondary, parent, false)
                SecondaryMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder<*>, position: Int) {
        val item = data[position]

        when (holder) {
            is PrimaryMessageViewHolder -> holder.bind(item)
            is SecondaryMessageViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].messageType

    fun submitNewData(it: List<MessageItemUi>) {
        data.clear()
        data.addAll(it)
        notifyDataSetChanged()
    }

    class PrimaryMessageViewHolder(val view: View) : MessageViewHolder<MessageItemUi>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: MessageItemUi) {
            messageContent.text = item.content
            messageContent.setTextColor(item.textColor)
        }
    }
    class SecondaryMessageViewHolder(val view: View) : MessageViewHolder<MessageItemUi>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: MessageItemUi) {
            messageContent.text = item.content
            messageContent.setTextColor(item.textColor)
        }


    }
}