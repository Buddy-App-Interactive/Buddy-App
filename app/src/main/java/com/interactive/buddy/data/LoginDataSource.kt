package com.interactive.buddy.data

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.interactive.buddy.data.interfaces.ServerListener
import com.interactive.buddy.data.model.LoggedInUser
import org.json.JSONObject
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(email: String, password: String, context: Context, callback: ServerListener) {
        try {
            val queue = Volley.newRequestQueue(context)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URLs.URL_LOGIN,
                { response ->
                    val userJson = JSONObject(response)

                    val user = LoggedInUser(
                        userJson.getString("id"),
                        userJson.getString("username"),
                        userJson.getString("email"),
                        null,
                        userJson.getString("jwt")
                    )
                    SharedPrefManager.getInstance(context).userLogin(user)
                    callback.onSuccess(user);
                },
                { error -> error.message?.let { Log.d("FROG", it) }
                    callback.onError(IOException("Error logging in")) })
            {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = email
                    params["password"] = password
                    return params
                }
            }
            queue.add(stringRequest);
        } catch (e: Throwable) {
            callback.onError(IOException("Error logging in", e))
        }
    }

    fun loginKey(key: String, context: Context, callback: ServerListener) {
        try {
            val queue = Volley.newRequestQueue(context)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URLs.URL_LOGIN,
                { response ->
                    val userJson = JSONObject(response)

                    Log.d("jaaaa", response)

                    val user = LoggedInUser(
                        userJson.getString("id"),
                        userJson.getString("username"),
                        null,
                        userJson.getString("loginKey"),
                        userJson.getString("jwt")
                    )
                    SharedPrefManager.getInstance(context).userLogin(user)
                    callback.onSuccess(user);
                },
                { error -> error.message?.let { Log.d("FROG", it) }
                    callback.onError(IOException("Error logging in")) })
            {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["loginKey"] = key
                    return params
                }
            }
            queue.add(stringRequest);
        } catch (e: Throwable) {
            callback.onError(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}