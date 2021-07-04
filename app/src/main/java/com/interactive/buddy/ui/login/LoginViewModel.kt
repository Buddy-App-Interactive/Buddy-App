package com.interactive.buddy.ui.login

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.buddy.R
import com.interactive.buddy.data.LoginDataSource
import com.interactive.buddy.data.interfaces.ServerListener
import com.interactive.buddy.data.model.LoggedInUser

class LoginViewModel(private val loginDataSource: LoginDataSource) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    private val _loginKeyForm = MutableLiveData<LoginKeyFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    val loginKeyFormState: LiveData<LoginKeyFormState> = _loginKeyForm

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


    fun login(email: String, password: String, context: Context) {
        loginDataSource.login(email, password, context, serverListener)
    }

    fun loginKey(key: String, context: Context) {
        loginDataSource.loginKey(key, context, serverListener)
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
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

    fun keyDataChanged(key: String) {
        _loginKeyForm.value = LoginKeyFormState(isDataValid = true)
    }
}