package com.interactive.buddy.ui.profile

import androidx.lifecycle.ViewModel
import com.interactive.buddy.services.ProfileService

class ProfileViewModel : ViewModel(){
    private lateinit var service: ProfileService

    public fun setService(service: ProfileService){
        this.service = service
    }

    public fun updateUserdata(){
        this.service.updateUserdata({},{})
    }

    fun updatePassword(password: String) {
        this.service.updatePassword(password,{},{})
    }
}