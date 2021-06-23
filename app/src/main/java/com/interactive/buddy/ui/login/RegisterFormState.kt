package com.interactive.buddy.ui.login

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(val usernameError: Int? = null,
                             val usernameKeyError: Int? = null,
                             val emailError: Int? = null,
                             val passwordError: Int? = null,
                             val passwordRepeatError: Int? = null,
                             val isDataValid: Boolean = false)