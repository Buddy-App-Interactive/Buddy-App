package com.interactive.buddy.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.interactive.buddy.R
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.services.ProfileService
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private lateinit var profileService: ProfileService
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileService = ProfileService(requireContext());
        viewModel.setService(profileService)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val user = SharedPrefManager.getInstance(this.requireContext()).user;
        usernameUpdate.setText(user.username)
        if(user.email != null)
            emailUpdate.setText(user.email)
        updatePasswordData.setOnClickListener {
            if(!passwordRepeatUpdate.text.isNullOrBlank()
                && !passwordUpdate.text.isNullOrBlank()) {
                if(passwordUpdate.text.toString() == passwordRepeatUpdate.text.toString()) {
                    viewModel.updatePassword(passwordUpdate.text.toString())
                    Snackbar.make(profileLayout, R.string.passwordUpdatedSnackbar, Snackbar.LENGTH_SHORT)
                        .show()
                }
                else
                    Snackbar.make(profileLayout, R.string.invalid_repeat_password, Snackbar.LENGTH_SHORT)
                        .show()
            }
            else{
                Snackbar.make(profileLayout, R.string.invalid_password, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        updateUserData.setOnClickListener{
            val user = SharedPrefManager.getInstance(this.requireContext()).user;
            if(!emailUpdate.text.isNullOrBlank())
                user.email = emailUpdate.text.toString()
            if(!usernameUpdate.text.isNullOrBlank())
                user.username = usernameUpdate.text.toString()
            SharedPrefManager.getInstance(this.requireContext()).userLogin(user)
            viewModel.updateUserdata()
            Snackbar.make(profileLayout, R.string.userdataUpdatedSnackbar, Snackbar.LENGTH_SHORT)
                .show()
        }
        logout.setOnClickListener {
            SharedPrefManager.getInstance(this.requireContext()).logout()
        }
    }
}