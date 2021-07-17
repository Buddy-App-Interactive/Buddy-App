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
import com.interactive.buddy.data.model.Message


class ChatService {
    lateinit var jwt: String;
    lateinit var ctx: Context;
    lateinit var queue: RequestQueue;

    constructor(ctx : Context) {
        jwt = SharedPrefManager.getInstance(ctx).user.jwt;
        queue = Volley.newRequestQueue(ctx)
        this.ctx = ctx;
    }

    fun getChats(successCallback: (MutableList<Chat>) -> Unit, errorCallback: (Exception) -> Unit) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, URLs.URL_CHATS,
            { response ->
                val type = object : TypeToken<List<Chat>>() {}.type
                val chats = Gson().fromJson<MutableList<Chat>>(response,type)
                successCallback.invoke(chats)
            },
            { error ->
                Log.d("NETWORKERROR:", error.toString())
                errorCallback.apply { error }
                errorCallback.invoke(error)
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $jwt"
                return params
            }}
        queue.add(stringRequest)
    }

    fun createChat(creatorId:String, requestId:String, successCallback: (Chat) -> Unit, errorCallback: (Exception) -> Unit) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URLs.URL_CHATS,
            { res ->
                val type = object : TypeToken<Chat>() {}.type
                successCallback.invoke(Gson().fromJson(res, type))
            },
            { error ->
                errorCallback.apply { error }
                errorCallback.invoke(error)
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $jwt"
                params["requestId"] = requestId
                params["creatorId"] = creatorId
                return params
            }}
        queue.add(stringRequest)
    }

    fun getKarma(creatorId:String, successCallback: (String) -> Unit, errorCallback: (Exception) -> Unit) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, URLs.URL_CHATS_KARMA,
            { response ->
                Log.d("Response", response.toString())
                val type = object : TypeToken<String>() {}.type
                successCallback.invoke(Gson().fromJson(response, type))
            },
            { error ->
                Log.d("NETWORKERROR:", error.toString())
                errorCallback.apply { error }
                errorCallback.invoke(error)
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $jwt"
                params["creatorId"] = creatorId
                return params
            }}
        queue.add(stringRequest)
    }
}