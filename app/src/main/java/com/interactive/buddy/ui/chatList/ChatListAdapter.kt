package com.interactive.buddy.ui.chatList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.data.model.Mood
import com.interactive.buddy.ui.chatList.ChatItemUi.Companion.TYPE_NORMAL_CHAT
import java.time.format.DateTimeFormatter


class ChatListAdapter() : RecyclerView.Adapter<ChatViewHolder<*>>() {
    val data = mutableListOf<ChatItemUi>()

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(chatId: String, username: String, moodResource: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_NORMAL_CHAT -> {
                val view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
                val viewholder = NormalChatViewHolder(view)
                view.setOnClickListener {
                    onItemClickListener?.onItemClick(
                        data[viewholder.adapterPosition]._id,
                        data[viewholder.adapterPosition].username,
                        when (data[viewholder.adapterPosition].mood) {
                        Mood.HAPPY -> R.drawable.ic_smiley_happy
                        Mood.OK -> R.drawable.ic_smiley_ok
                        Mood.SAD -> R.drawable.ic_smiley_sad
                    }
                    )
                    ;
                }
                viewholder
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

    fun submitNewData(it: List<ChatItemUi>) {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
    }

    class NormalChatViewHolder(val view: View) : ChatViewHolder<ChatItemUi>(view) {
        private val content = view.findViewById<TextView>(R.id.chat_username)
        private val lastMessage = view.findViewById<TextView>(R.id.lastMessageChatList)
        private val timeLastMessage = view.findViewById<TextView>(R.id.timeChatList)
        private val imageMood = view.findViewById<ImageView>(R.id.moodImageChatList)

        override fun bind(item: ChatItemUi) {
            content.text = item.username
            lastMessage.text = item.lastMessage.getMessageContent()
            timeLastMessage.text = item.lastMessage.getCreatedDate().format(DateTimeFormatter.ofPattern("HH:mm"))

            when (item.mood) {
                Mood.HAPPY -> imageMood.setImageResource(R.drawable.ic_smiley_happy)
                Mood.OK -> imageMood.setImageResource(R.drawable.ic_smiley_ok)
                Mood.SAD -> imageMood.setImageResource(R.drawable.ic_smiley_sad)
            }
        }
    }
}