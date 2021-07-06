package com.interactive.buddy.ui.login.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.interactive.buddy.R
import com.interactive.buddy.data.App
import com.interactive.buddy.databinding.FragmentLoginBinding
import com.interactive.buddy.ui.login.LoggedInUserView
import com.interactive.buddy.ui.login.LoginViewModel
import com.interactive.buddy.ui.login.LoginViewModelFactory
import com.interactive.buddy.ui.navigation.NavigationActivity

class LoginFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentLoginBinding? = null
    private lateinit var loginViewModel: LoginViewModel
    private var fragment = this;
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root = binding.root

        val login: Button = binding.login
        val loginKey: Button = binding.loginKey
        val username: TextInputEditText = binding.emailLogin
        val key: TextInputEditText = binding.keyLogin
        val password: TextInputEditText = binding.passwordLogin
        val loading: ProgressBar = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this.viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginKeyFormState.observe(this.viewLifecycleOwner, Observer {
            val loginKeyState = it ?: return@Observer

            // disable login button unless both username / password is valid
            loginKey.isEnabled = loginKeyState.isDataValid

            if (loginKeyState.keyError != null) {
                username.error = getString(loginKeyState.keyError)
            }
        })

        loginViewModel.loginResult.observe(this.viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            this.parentFragmentManager.setFragmentResult("login", Bundle())
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }


        key.afterTextChanged {
            loginViewModel.keyDataChanged(
                key.text.toString()
            )
        }
        loginKey.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.loginKey(key.text.toString(), fragment.requireContext())
        }


        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString(),
                            fragment.requireContext()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString(), fragment.requireContext())
            }

            return root
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): LoginFragment {
            return LoginFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.username
        Toast.makeText(
            this.context,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()

        var app = App();
        app.loginDone()

        val myIntent = Intent(this.requireContext(), NavigationActivity::class.java)
        this.startActivity(myIntent)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}