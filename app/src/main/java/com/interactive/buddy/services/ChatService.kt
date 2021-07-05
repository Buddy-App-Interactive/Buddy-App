package com.interactive.buddy.services

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.data.URLs
import com.interactive.buddy.data.model.Chat
import com.interactive.buddy.data.model.request.Request


class ChatService {
    lateinit var jwt: String;
    lateinit var ctx: Context;
    lateinit var queue: RequestQueue;

    constructor(ctx : Context) {
        jwt = SharedPrefManager.getInstance(ctx).user.jwt;
        queue = Volley.newRequestQueue(ctx)
        this.ctx = ctx;
    }

    fun getChats(successCallback: (List<Request>) -> Unit, errorCallback: () -> Unit) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, URLs.URL_CHATS,
            { response ->
                val requests = Gson().fromJson<List<Request>>(response, listOf<Chat>()::class.java);
                successCallback.invoke(requests)
            },
            { error ->
                errorCallback.apply { error }
                errorCallback.invoke()
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = jwt
                return params
            }}
        queue.add(stringRequest)
    }
}