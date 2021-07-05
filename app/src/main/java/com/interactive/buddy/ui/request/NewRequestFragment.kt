package com.interactive.buddy.ui.request

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.interactive.buddy.R
import com.interactive.buddy.data.model.request.RequestType

class NewRequestFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var spType: Spinner
    private lateinit var editLimit: EditText
    private lateinit var editAdditionalInformation: EditText
    private lateinit var editLanguages: EditText
    private lateinit var btnSaveLanguage: Button
    private var selectedType: RequestType = RequestType.HAPPY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        editAdditionalInformation = view.findViewById(R.id.editAdditionalInformation)
        editLanguages = view.findViewById(R.id.editLanguages)
        btnSaveLanguage = view.findViewById(R.id.btnCreateRequest)

        btnSaveLanguage.setOnClickListener(this)

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

    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnSaveLanguage) {

        }
    }
}