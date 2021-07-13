package com.interactive.buddy.ui.request.ListItems

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.interactive.buddy.R
import com.interactive.buddy.data.model.request.Request
import com.interactive.buddy.data.model.request.RequestType

class OpenRequestsListAdapter(private var c: Context, private var requests: List<Request>) : BaseAdapter() {

    override fun getCount(): Int   {  return requests.size  }
    override fun getItem(i: Int): Any {  return requests[i] }
    override fun getItemId(i: Int): Long { return i.toLong()}

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            //inflate layout resource if its null
            view = LayoutInflater.from(c).inflate(R.layout.open_requests_list_item, viewGroup, false)
        }

        val tvCreator = view!!.findViewById(R.id.creator) as TextView
        val imageView = view.findViewById(R.id.icon) as ImageView
        val tvType = view!!.findViewById(R.id.type) as TextView
        val tvTimeStamp = view!!.findViewById(R.id.timestamp) as TextView

        val current = this.getItem(i) as Request

        when (current.type) {
            RequestType.BORED -> imageView.setImageResource(R.drawable.ic_smiley_ok)
            RequestType.JUSTTALK -> imageView.setImageResource(R.drawable.ic_smiley_ok)
            RequestType.HAPPY -> imageView.setImageResource(R.drawable.ic_smiley_happy)
            RequestType.DEPRESSED -> imageView.setImageResource(R.drawable.ic_smiley_sad)
        }
        tvCreator.text = current.description
        tvType.text = current.type.toString()
        tvTimeStamp.text = current.id_creator
        imageView

        //handle itemclicks for the ListView
        //view.setOnClickListener { Toast.makeText(c, current.id.toString(), Toast.LENGTH_SHORT).show() }

        return view
    }
}