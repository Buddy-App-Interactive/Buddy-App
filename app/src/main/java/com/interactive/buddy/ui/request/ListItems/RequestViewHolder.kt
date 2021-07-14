package com.interactive.buddy.ui.request.ListItems

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class RequestViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}