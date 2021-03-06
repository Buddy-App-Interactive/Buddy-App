package com.interactive.buddy.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.interactive.buddy.R
import com.interactive.buddy.data.LoginDataSource
import com.interactive.buddy.data.RegisterDataSource
import com.interactive.buddy.data.interfaces.ServerListener
import com.interactive.buddy.data.model.LoggedInUser
import java.lang.Exception
import java.util.*

class RegisterViewModel(private val registerDataSource: RegisterDataSource) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    private val _registerKeyForm = MutableLiveData<RegisterKeyFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm
    val registerKeyFormState: LiveData<RegisterKeyFormState> = _registerKeyForm
    lateinit var key: String

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    var serverListener = object : ServerListener {
        override fun onSuccess(loggedInUser: LoggedInUser) {
            _registerResult.value = RegisterResult(success = LoggedInUserView(username = loggedInUser.username))
        }

        override fun onError(exception: Exception) {
            _registerResult.value = RegisterResult(error = R.string.login_failed)
        }

    }


    fun register(username: String, email: String, password: String, context: Context) {
        registerDataSource.register(username, email, password, context, serverListener)
    }

    fun registerKey(username: String, context: Context) {
        registerDataSource.registerKey(username, context, serverListener)
    }

    fun loginDataChanged(username: String, email: String, password: String, passwordRepeat: String) {
        if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        }
        else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        }
        else if (password != passwordRepeat) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_repeat_password)
        }
        else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }


    fun loginKeyDataChanged(username: String) {
            _registerKeyForm.value = RegisterKeyFormState(isDataValid = true)
    }

    // A placeholder username validation check
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}