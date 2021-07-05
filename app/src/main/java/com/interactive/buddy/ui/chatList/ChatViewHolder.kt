package com.interactive.buddy.ui.chatList

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ChatViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}