package com.interactive.buddy.ui.request

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.data.model.request.Request
import com.interactive.buddy.data.model.request.RequestType
import com.interactive.buddy.services.RequestService
import java.time.LocalDateTime
import java.util.*

class NewRequestFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var spType: Spinner
    private lateinit var editLimit: EditText
    private lateinit var editDescription: EditText
    private lateinit var editLanguages: EditText
    private lateinit var btnCreateRequest: Button
    private var selectedType: RequestType = RequestType.HAPPY
    private lateinit var requestService : RequestService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestService = RequestService(requireContext())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_request, container, false)
    }

    /**
     * Finding views by id.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        spType = view.findViewById(R.id.spType)
        editLimit = view.findViewById(R.id.editLimit)
        editDescription = view.findViewById(R.id.editDescription)
        editLanguages = view.findViewById(R.id.editLanguages)
        btnCreateRequest = view.findViewById(R.id.btnCreateRequest)
        btnCreateRequest.setOnClickListener(this)

        val dataAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            RequestType.values())
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spType.adapter = dataAdapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedType = RequestType.values()[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnCreateRequest) {
            if (editDescription.text.toString().isNotEmpty() && editLimit.text.toString().toString()
                    .isNotEmpty()
            ) {
                val type = RequestType.values().get(spType.selectedItemPosition)
                //val userId = UUID.fromString(SharedPrefManager.getInstance(requireContext()).user.userId)
                val request = Request(UUID.randomUUID(), UUID.randomUUID(), editDescription.text.toString(), type, editLimit.text.toString().toInt(), LocalDateTime.now());
                requestService.createRequest({createdRequest ->
                    //yes
                }, {
                    //yes
                }, request)

                val fragment: Fragment = RequestFragment.newInstance(true)
                val fm: FragmentManager = requireActivity().supportFragmentManager
                val transaction: FragmentTransaction = fm.beginTransaction()
                transaction.replace(R.id.container, fragment)
                transaction.commit()
            } else {
                Snackbar.make(requireActivity().findViewById(R.id.container), "Please enter the needed data", Snackbar.LENGTH_LONG).show();
            }
         }
    }
}