package com.interactive.buddy.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginKeyFormState(val keyError: Int? = null,
                             val isDataValid: Boolean = false)