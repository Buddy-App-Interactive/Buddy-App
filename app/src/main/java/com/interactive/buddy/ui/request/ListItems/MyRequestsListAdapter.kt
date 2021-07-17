package com.interactive.buddy.ui.request.ListItems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.data.model.request.RequestType
import com.interactive.buddy.ui.request.ListItems.RequestItemUI.Companion.TYPE_MY_REQUEST
import com.interactive.buddy.ui.request.ListItems.RequestItemUI.Companion.TYPE_OPEN_REQUEST


class MyRequestsListAdapter(fragmentActivity: FragmentActivity) : RecyclerView.Adapter<RequestViewHolder<*>>() {
    val data = mutableListOf<RequestItemUI>()
    var act = fragmentActivity

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
                val viewholder = MyRequestViewHolder(view, act)
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

    class MyRequestViewHolder(val view: View, activity: FragmentActivity) : RequestViewHolder<RequestItemUI>(view) {
        private val username = view.findViewById<TextView>(R.id.myRequest_username)
        private val desc = view.findViewById<TextView>(R.id.myRequest_description)
        private val time = view.findViewById<TextView>(R.id.timeMyRequestList)
        private val imageMood = view.findViewById<ImageView>(R.id.moodMyRequestList)
        private val editRequest = view.findViewById<ImageView>(R.id.editMyRequest)
        private val act = activity

        override fun bind(item: RequestItemUI) {
            username.text = item.request.creator.username
            desc.text = item.request.description
            time.text = item.request.type.name


            editRequest.setOnClickListener {
                SharedPrefManager.getInstance(act).isEditRequest = true
                SharedPrefManager.getInstance(act).editRequest = item.request
                act.findNavController(R.id.nav_host_fragment_activity_navigation).navigate(R.id.navigation_new_request)
            }

            when (item.request.type) {
                RequestType.BORED -> imageMood.setImageResource(R.drawable.ic_smiley_ok)
                RequestType.JUSTTALK -> imageMood.setImageResource(R.drawable.ic_smiley_ok)
                RequestType.HAPPY -> imageMood.setImageResource(R.drawable.ic_smiley_happy)
                RequestType.DEPRESSED -> imageMood.setImageResource(R.drawable. ic_smiley_sad)
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