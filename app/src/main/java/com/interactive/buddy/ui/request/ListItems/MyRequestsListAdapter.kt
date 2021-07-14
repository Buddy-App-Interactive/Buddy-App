package com.interactive.buddy.ui.request.ListItems

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.data.model.request.RequestType
import com.interactive.buddy.ui.request.ListItems.RequestItemUI.Companion.TYPE_MY_REQUEST
import com.interactive.buddy.ui.request.ListItems.RequestItemUI.Companion.TYPE_OPEN_REQUEST


class MyRequestsListAdapter() : RecyclerView.Adapter<RequestViewHolder<*>>() {
    val data = mutableListOf<RequestItemUI>()

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(requestId: String, creatorId: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_MY_REQUEST -> {
                val view = LayoutInflater.from(context).inflate(R.layout.my_requests_list_item, parent, false)
                val viewholder = MyRequestViewHolder(view)
                view.setOnClickListener {
                    onItemClickListener?.onItemClick(data[viewholder.adapterPosition].request._id,data[viewholder.adapterPosition].request.creator._id);
                }
                viewholder
            }
            TYPE_OPEN_REQUEST -> {
                val view = LayoutInflater.from(context).inflate(R.layout.open_requests_list_item, parent, false)
                val viewholder = OpenRequestViewHolder(view)
                view.setOnClickListener {
                    onItemClickListener?.onItemClick(data[viewholder.adapterPosition].request._id,data[viewholder.adapterPosition].request.creator._id)
                }
                viewholder
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RequestViewHolder<*>, position: Int) {
        val item = data[position]
        when (holder) {
            is MyRequestViewHolder -> holder.bind(item)
            is OpenRequestViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].requestType

    fun submitNewData(it: List<RequestItemUI>) {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
    }

    class MyRequestViewHolder(val view: View) : RequestViewHolder<RequestItemUI>(view) {
        private val username = view.findViewById<TextView>(R.id.myRequest_username)
        private val desc = view.findViewById<TextView>(R.id.myRequest_description)
        private val time = view.findViewById<TextView>(R.id.timeMyRequestList)
        private val imageMood = view.findViewById<ImageView>(R.id.moodMyRequestList)

        override fun bind(item: RequestItemUI) {
            username.text = item.request.creator.username
            desc.text = item.request.description
            time.text = item.request.type.name

            when (item.request.type) {
                RequestType.BORED -> imageMood.setImageResource(R.drawable.ic_smiley_ok)
                RequestType.JUSTTALK -> imageMood.setImageResource(R.drawable.ic_smiley_ok)
                RequestType.HAPPY -> imageMood.setImageResource(R.drawable.ic_smiley_happy)
                RequestType.DEPRESSED -> imageMood.setImageResource(R.drawable.ic_smiley_sad)
            }
        }
    }

    class OpenRequestViewHolder(val view: View) : RequestViewHolder<RequestItemUI>(view) {
        private val username = view.findViewById<TextView>(R.id.Request_username)
        private val desc = view.findViewById<TextView>(R.id.Request_description)
        private val time = view.findViewById<TextView>(R.id.timeRequestList)
        private val imageMood = view.findViewById<ImageView>(R.id.moodRequestList)

        override fun bind(item: RequestItemUI) {
            username.text = item.request.creator.username
            desc.text = item.request.description
            time.text = item.request.type.name

            when (item.request.type) {
                RequestType.BORED -> imageMood.setImageResource(R.drawable.ic_smiley_ok)
                RequestType.JUSTTALK -> imageMood.setImageResource(R.drawable.ic_smiley_ok)
                RequestType.HAPPY -> imageMood.setImageResource(R.drawable.ic_smiley_happy)
                RequestType.DEPRESSED -> imageMood.setImageResource(R.drawable.ic_smiley_sad)
            }
        }
    }
}