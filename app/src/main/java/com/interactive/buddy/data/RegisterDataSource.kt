package com.interactive.buddy.data

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.interactive.buddy.data.interfaces.ServerListener
import com.interactive.buddy.data.model.LoggedInUser
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.HashMap


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class RegisterDataSource {

    fun register(username: String, email: String, password: String, context: Context, callback: ServerListener) {
        try {
            val queue = Volley.newRequestQueue(context)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URLs.URL_REGISTER,
                { response ->
                    val userJson = JSONObject(response)

                    val user = LoggedInUser(
                        userJson.getString("id"),
                        userJson.getString("username"),
                        userJson.getString("email"),
                        null,
                        userJson.getString("jwt"),
                        userJson.getInt("mood"),
                    )
                    SharedPrefManager.getInstance(context).userLogin(user)
                    callback.onSuccess(user);
                },
                { error -> error.message?.let { Log.d("ERROR", it) }
                    callback.onError(IOException("Error logging in")) })
            {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = email
                    params["username"] = username
                    params["password"] = password
                    return params
                }
            }
            queue.add(stringRequest);
        } catch (e: Throwable) {
            callback.onError(IOException("Error logging in", e))
        }
    }

    fun registerKey(username: String, context: Context, callback: ServerListener) {
        try {
            val queue = Volley.newRequestQueue(context)
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URLs.URL_REGISTER,
                { response ->
                    val userJson = JSONObject(response)

                    val user = LoggedInUser(
                        userJson.getString("id"),
                        userJson.getString("username"),
                        userJson.getString("email"),
                        userJson.getString("loginKey"),
                        userJson.getString("jwt"),
                        userJson.getInt("mood"),
                    )
                    SharedPrefManager.getInstance(context).userLogin(user)
                    callback.onSuccess(user);
                },
                { error -> error.message?.let { Log.d("ERROR", it) }
                    callback.onError(IOException("Error logging in")) })
            {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["username"] = username
                    return params
                }
            }
            queue.add(stringRequest);
        } catch (e: Throwable) {
            callback.onError(IOException("Error logging in", e))
        }
    }
}