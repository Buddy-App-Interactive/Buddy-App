package com.interactive.buddy.ui.request

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.interactive.buddy.R
import com.interactive.buddy.data.model.Mood
import com.interactive.buddy.services.ChatService
import com.interactive.buddy.services.RequestService
import com.interactive.buddy.ui.chat.ui.ChatActivity
import com.interactive.buddy.ui.chatList.ChatListAdapter
import com.interactive.buddy.ui.navigation.NavigationActivity
import com.interactive.buddy.ui.request.ListItems.MyRequestsListAdapter
import com.interactive.buddy.ui.request.ListItems.RequestItemUI
import com.interactive.buddy.ui.request.ListItems.RequestItemUI.Companion.TYPE_MY_REQUEST
import com.interactive.buddy.ui.request.ListItems.RequestItemUI.Companion.TYPE_OPEN_REQUEST
import kotlinx.android.synthetic.main.fragment_request.*
import kotlinx.android.synthetic.main.fragment_request.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val IS_OPEN_REQUESTS = "isOpenRequests"

/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var isOpenRequests: Boolean = false
    private lateinit var fabCreateRequest: FloatingActionButton;
    private lateinit var btnMyRequests: Button;

    private lateinit var requestService : RequestService
    private lateinit var chatService : ChatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestService = RequestService(requireContext())
        chatService = ChatService(requireContext())
        displayRequests(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    /**
     * Finding views by id.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fabCreateRequest = view.findViewById(R.id.fabCreateRequest);
        btnMyRequests = view.findViewById(R.id.btnMyRequests);
        rvRequests.layoutManager = LinearLayoutManager(context)

        btnMyRequests.setOnClickListener(this);
        fabCreateRequest.setOnClickListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RequestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(isOpenRequests: Boolean) =
            RequestFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_OPEN_REQUESTS, isOpenRequests)
                }
            }
    }

    private fun displayRequests(isOwn: Boolean){
        if(!isOwn) {
            // Fetch requests.
            requestService.getRequests({ requests ->
                val requestAdapter = MyRequestsListAdapter(requireActivity())
                val requestUI: MutableList<RequestItemUI> = mutableListOf()
                for (req in requests) {
                    requestUI.add(RequestItemUI(req, TYPE_OPEN_REQUEST))
                }
                requestAdapter.submitNewData(requestUI)
                val fragment = this
                requestAdapter.setOnItemClickListener(object : MyRequestsListAdapter.OnItemClickListener {
                    override fun onItemClick(requestId: String, creatorId: String) {
                        chatService.createChat(creatorId,requestId,{ chat ->
                            val myIntent = Intent(fragment.requireContext(), ChatActivity::class.java)
                            myIntent.putExtra("chatId",chat._id)
                            myIntent.putExtra("chatName",chat.username)
                            myIntent.putExtra("moodResource",
                                when (chat.mood) {
                                3 -> R.drawable.ic_smiley_happy
                                2 -> R.drawable.ic_smiley_ok
                                1 -> R.drawable.ic_smiley_sad
                                    else -> R.drawable.ic_smiley_happy
                                })
                            fragment.startActivity(myIntent)
                        },{

                        })
                    }
                })
                rvRequests.adapter = requestAdapter;
            }, {}, false)
            ((requireActivity() as NavigationActivity).supportActionBar)!!.title = "Open requests"
        }else{
            requestService.getRequests({ requests ->
                val requestAdapter = MyRequestsListAdapter(requireActivity())
                val requestUI: MutableList<RequestItemUI> = mutableListOf()
                for (req in requests){
                    requestUI.add(RequestItemUI(req,TYPE_MY_REQUEST))
                }
                requestAdapter.submitNewData(requestUI)
                rvRequests.adapter = requestAdapter;
                ((requireActivity() as NavigationActivity).supportActionBar)!!.title = "My requests"
            },
            {
            }, true)
        }
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnMyRequests) {
            isOpenRequests = !isOpenRequests
            displayRequests(isOpenRequests)
        } else if (v!!.id == R.id.fabCreateRequest) {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_navigation).navigate(R.id.navigation_new_request)
        }
    }

    override fun onResume() {
        super.onResume()
        displayRequests(isOpenRequests)
    }
}