package com.interactive.buddy.ui.chatList

import android.view.View
import android.widget.TextView
import com.interactive.buddy.R

class NormalViewHolder(val view: View) : ChatViewHolder<ChatItemUi>(view) {
    private val content = view.findViewById<TextView>(R.id.chat_username)

    override fun bind(item: ChatItemUi) {
        content.text = item.content
    }
}