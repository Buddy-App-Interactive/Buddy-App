package com.interactive.buddy.ui.chat

import android.view.View
import android.widget.TextView
import com.interactive.buddy.R

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