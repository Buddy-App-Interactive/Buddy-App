package com.interactive.buddy.services

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.data.URLs
import com.interactive.buddy.data.model.Chat
import com.interactive.buddy.data.model.LoggedInUser
import com.interactive.buddy.data.model.request.Request


class MoodService {
    lateinit var jwt: String;
    lateinit var ctx: Context;
    lateinit var queue: RequestQueue;
    lateinit var user:LoggedInUser;

    constructor(ctx : Context) {
        user = SharedPrefManager.getInstance(ctx).user;
        jwt = user.jwt;
        queue = Volley.newRequestQueue(ctx)
        this.ctx = ctx;
    }

    fun changeMood(successCallback: (Request) -> Unit, errorCallback: () -> Unit) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URLs.URL_MOODS,
            { response ->
                val createdRequest = Gson().fromJson(response, Request::class.java);
                successCallback.invoke(createdRequest)
            },
            { error ->
                errorCallback.apply { error }
                errorCallback.invoke()
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                user = SharedPrefManager.getInstance(ctx).user
                params["Authorization"] = "Bearer $jwt"
                params["user_id"] = user.userId
                params["mood"] = user.mood.toString()
                return params
            }
        }
        queue.add(stringRequest)
    }
}