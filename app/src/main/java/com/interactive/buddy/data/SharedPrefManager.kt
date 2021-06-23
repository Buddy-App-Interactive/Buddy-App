package com.interactive.buddy.data

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.interactive.buddy.data.model.LoggedInUser
import com.interactive.buddy.ui.login.LoginRegisterActivity

class SharedPrefManager private constructor(private var mCtx: Context) {
    fun userLogin(user: LoggedInUser) {
        val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
            SHARED_PREF_NAME, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ID, user.userId)
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_EMAIL, user.email)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE
            )
            return sharedPreferences.getString(KEY_USERNAME, null) != null
        }
    val user: LoggedInUser
        get() {
            val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
                SHARED_PREF_NAME, Context.MODE_PRIVATE
            )
            return LoggedInUser(
                sharedPreferences.getString(KEY_ID, null)!!,
                sharedPreferences.getString(KEY_USERNAME, null)!!,
                sharedPreferences.getString(KEY_EMAIL, null)!!
            )
        }

    //this method will logout the user
    fun logout() {
        val sharedPreferences: SharedPreferences = mCtx.getSharedPreferences(
            SHARED_PREF_NAME, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        mCtx.startActivity(Intent(mCtx, LoginRegisterActivity::class.java))
    }

    companion object {
        //the constants
        private const val SHARED_PREF_NAME = "buddysharedpref"
        private const val KEY_USERNAME = "keyusername"
        private const val KEY_EMAIL = "keyemail"
        private const val KEY_ID = "keyid"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance!!
        }
    }
}