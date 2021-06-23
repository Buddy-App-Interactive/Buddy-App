package com.interactive.buddy.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.interactive.buddy.R
import com.interactive.buddy.data.LoginDataSource
import com.interactive.buddy.data.interfaces.ServerListener
import com.interactive.buddy.data.model.LoggedInUser
import java.lang.Exception

class LoginViewModel(private val loginDataSource: LoginDataSource) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    var serverListener = object : ServerListener {
        override fun onSuccess(loggedInUser: LoggedInUser) {
            _loginResult.value = LoginResult(success = LoggedInUserView(username = loggedInUser.username))
        }

        override fun onError(exception: Exception) {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }

    }


    fun login(username: String, password: String, context: Context) {
        loginDataSource.login(username, password, context, serverListener)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}