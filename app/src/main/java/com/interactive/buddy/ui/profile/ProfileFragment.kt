package com.interactive.buddy.ui.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager


class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var editUsername: EditText
    private lateinit var editEmail: EditText
    private lateinit var btnSaveProfile: Button
    private lateinit var editPassword: EditText
    private lateinit var editRepeatPassword: EditText
    private lateinit var btnChangePassword: Button
    private lateinit var editLanguages: EditText
    private lateinit var btnSaveLanguage: Button
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    /**
     * Finding views by id.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editUsername = view.findViewById(R.id.editUsername)
        editEmail = view.findViewById(R.id.editEmail)
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile)
        editPassword = view.findViewById(R.id.editPassword)
        editRepeatPassword = view.findViewById(R.id.editRepeatPassword)
        btnChangePassword = view.findViewById(R.id.btnChangePassword)
        editLanguages = view.findViewById(R.id.editLanguages)
        btnSaveLanguage = view.findViewById(R.id.btnSaveLanguage)
        btnLogout = view.findViewById(R.id.logoutButton)


        btnSaveProfile.setOnClickListener(this)
        btnChangePassword.setOnClickListener(this)
        btnSaveLanguage.setOnClickListener(this)
        btnLogout.setOnClickListener {
            SharedPrefManager.getInstance(this.requireContext()).logout()
        }
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnSaveProfile) {

        } else if (v!!.id == R.id.btnChangePassword) {

        } else if (v!!.id == R.id.btnSaveLanguage) {

        }
    }

}