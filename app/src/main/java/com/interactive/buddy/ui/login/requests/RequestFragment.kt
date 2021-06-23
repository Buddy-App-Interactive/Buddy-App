package com.interactive.buddy.ui.login.requests

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.interactive.buddy.R
import com.interactive.buddy.data.RequestStore
import com.interactive.buddy.ui.login.requests.ListItems.OpenRequestsListAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val IS_OPEN_REQUESTS = "isOpenRequests"

/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var isOpenRequests: Boolean = false
    private lateinit var fabCreateRequest: FloatingActionButton;
    private lateinit var btnMyRequests: FloatingActionButton;
    private lateinit var lvRequests: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isOpenRequests = it.getBoolean(IS_OPEN_REQUESTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    /**
     * Finding views by id.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fabCreateRequest = view.findViewById(R.id.fabCreateRequest);
        btnMyRequests = view.findViewById(R.id.btnMyRequests);
        lvRequests = view.findViewById(R.id.lvRequests) as ListView;

        // If not open requests view hide my requests button.
        if(!isOpenRequests) {
            btnMyRequests.visibility = View.INVISIBLE;
        }

        val requestListAdapter = OpenRequestsListAdapter(this.requireContext(), RequestStore.getRequests())
        lvRequests.adapter = requestListAdapter;
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
}