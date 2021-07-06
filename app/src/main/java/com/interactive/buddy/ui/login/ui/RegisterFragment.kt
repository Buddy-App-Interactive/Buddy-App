package com.interactive.buddy.ui.login.ui

import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.interactive.buddy.R
import com.interactive.buddy.data.App
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.databinding.FragmentRegisterBinding
import com.interactive.buddy.ui.login.LoggedInUserView
import com.interactive.buddy.ui.login.LoginViewModelFactory
import com.interactive.buddy.ui.login.RegisterViewModel
import com.interactive.buddy.ui.navigation.NavigationActivity


class RegisterFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentRegisterBinding? = null
    private lateinit var registerViewModel: RegisterViewModel
    private var fragment = this;

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root = binding.root

        val register: Button = binding.register
        val registerKey: Button = binding.registerKey
        val email: TextInputEditText = binding.emailRegister
        val password: TextInputEditText = binding.passwordRegister
        val passwordRepeat: TextInputEditText = binding.passwordRepeatRegister
        val username: TextInputEditText = binding.usernameRegister
        val usernameKey: TextInputEditText = binding.usernameRegisterKey
        val loading: ProgressBar = binding.loading

        registerViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this.viewLifecycleOwner, Observer {
            val registerState = it ?: return@Observer

            register.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
            if (registerState.passwordRepeatError != null) {
                passwordRepeat.error = getString(registerState.passwordRepeatError)
            }
            if (registerState.emailError != null) {
                email.error = getString(registerState.emailError)
            }
        })

        registerViewModel.registerKeyFormState.observe(this.viewLifecycleOwner, Observer {
            val registerState = it ?: return@Observer

            registerKey.isEnabled = registerState.isDataValid

            if (registerState.usernameKeyError != null) {
                usernameKey.error = getString(registerState.usernameKeyError)
            }
        })

        registerViewModel.registerResult.observe(this.viewLifecycleOwner, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                updateUiWithUser(registerResult.success)
            }
            this.parentFragmentManager.setFragmentResult("register", Bundle())
        })

        email.afterTextChanged {
            registerViewModel.loginDataChanged(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                passwordRepeat.text.toString()
            )
        }

        usernameKey.afterTextChanged {
            registerViewModel.loginKeyDataChanged(
                usernameKey.text.toString()
            )
        }


        registerKey.setOnClickListener {
            loading.visibility = View.VISIBLE
            registerViewModel.registerKey(usernameKey.text.toString(), fragment.requireContext())
        }

        password.apply {
            afterTextChanged {
                registerViewModel.loginDataChanged(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    passwordRepeat.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            username.text.toString(),
                            email.text.toString(),
                            password.text.toString(),
                            fragment.requireContext()
                        )
                }
                false
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                registerViewModel.register(username.text.toString(), email.text.toString(), password.text.toString(), fragment.requireContext())
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
        fun newInstance(sectionNumber: Int): RegisterFragment {
            return RegisterFragment().apply {
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

        val key = SharedPrefManager.getInstance(this.requireContext()).user.key
        if(key != null){
            val builder: AlertDialog.Builder = AlertDialog.Builder(this.requireContext(), R.style.DialogDarkTheme)
            builder.setMessage(R.string.copy_key_message)
            builder.setPositiveButton(
                R.string.copy
            ) { dialog, id ->
                val myClipboard: ClipboardManager? = this.requireContext()
                    .getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                val myClip: ClipData = ClipData.newPlainText("key", key)
                myClipboard!!.setPrimaryClip(myClip)
                dialog.cancel()
                var app = App();
                app.loginDone()
                val myIntent = Intent(this.requireContext(), NavigationActivity::class.java)
                this.startActivity(myIntent)
            }


            val alert: AlertDialog = builder.create()
            alert.show()
        }
        else{
            var app = App();
            app.loginDone()
            val myIntent = Intent(this.requireContext(), NavigationActivity::class.java)
            this.startActivity(myIntent)
        }
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}