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
import com.interactive.buddy.data.model.Message


class MessageService {
    var jwt: String;
    var ctx: Context;
    var queue: RequestQueue;

    constructor(ctx : Context) {
        jwt = SharedPrefManager.getInstance(ctx).user.jwt;
        queue = Volley.newRequestQueue(ctx)
        this.ctx = ctx;
    }

    fun getMessages(chat_id: String,successCallback: (MutableList<Message>) -> Unit, errorCallback: (Exception) -> Unit) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, URLs.URL_MESSAGES,
            { response ->
                var messages = mutableListOf<Message>()
                try {
                    val type = object : TypeToken<MutableList<Message>>() {}.type
                     messages = Gson().fromJson(response, type)
                }catch (ex:Exception){
                    ex.message?.let { Log.d("error", it) }
                }
                successCallback.invoke(messages)
            },
            { error ->
                errorCallback.apply { error }
                errorCallback.invoke(error)
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $jwt"
                params["chat_id"] = chat_id
                return params
            }
        }
        queue.add(stringRequest)
    }

    fun sendMessage(chat_id: String, content: String ,successCallback: () -> Unit, errorCallback: (Exception) -> Unit) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URLs.URL_SEND_MESSAGE,
            { response ->
            },
            { error ->
                errorCallback.apply { error }
                errorCallback.invoke(error)
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $jwt"
                params["chat_id"] = chat_id
                params["content"] = content
                return params
            }
        }
        queue.add(stringRequest)
    }
}