package com.interactive.buddy.ui.login.requests

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.interactive.buddy.R
import com.interactive.buddy.services.RequestService
import com.interactive.buddy.ui.login.requests.ListItems.OpenRequestsListAdapter
import com.interactive.buddy.ui.login.requests.ListItems.YourRequestsListAdapter
import com.interactive.buddy.ui.login.ui.main.NavigationActivity
import com.interactive.buddy.ui.request.NewRequestFragment


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
    private var isOpenRequests: Boolean = true
    private lateinit var fabCreateRequest: FloatingActionButton;
    private lateinit var btnMyRequests: Button;
    private lateinit var lvRequests: ListView

    private lateinit var requestService : RequestService

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
        requestService = RequestService(requireContext());

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

            // Fetch requests.
            requestService.getRequests({ requests ->
                val requestListAdapter = YourRequestsListAdapter(this.requireContext(), requests)
                lvRequests.adapter = requestListAdapter;
            },
            {
                // TODO: handle or whatever
            })

            ((requireActivity() as NavigationActivity).supportActionBar)!!.title = "Open requests"
        }

        else {
            // Fetch requests.
            requestService.getRequests({ requests ->
                val requestListAdapter = OpenRequestsListAdapter(this.requireContext(), requests)
                lvRequests.adapter = requestListAdapter;
            },
            {
                // TODO: handle or whatever
            }, true)

            ((requireActivity() as NavigationActivity).supportActionBar)!!.title = "My requests"
        }

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

    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnMyRequests) {
            val fragment: Fragment = RequestFragment.newInstance(false)

            val fm: FragmentManager = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        } else if (v!!.id == R.id.fabCreateRequest) {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()
            transaction.replace(R.id.container, NewRequestFragment())
            transaction.commit()
        }
    }
}