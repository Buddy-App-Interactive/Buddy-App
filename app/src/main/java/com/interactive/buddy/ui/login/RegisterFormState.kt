package com.interactive.buddy.ui.login

/**
 * Data validation state of the register form.
 */
data class RegisterFormState(val usernameError: Int? = null,
                             val emailError: Int? = null,
                             val passwordError: Int? = null,
                             val passwordRepeatError: Int? = null,
                             val isDataValid: Boolean = false)