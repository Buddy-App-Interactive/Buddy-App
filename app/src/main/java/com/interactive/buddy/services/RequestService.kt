package com.interactive.buddy.services

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.interactive.buddy.data.SharedPrefManager
import com.interactive.buddy.data.URLs
import com.interactive.buddy.data.model.request.Request


class RequestService {
    lateinit var jwt: String;
    lateinit var ctx: Context;
    lateinit var queue: RequestQueue;

    constructor(ctx : Context) {
        jwt = SharedPrefManager.getInstance(ctx).user.jwt;
        queue = Volley.newRequestQueue(ctx)
        this.ctx = ctx;
    }

    fun getRequests(successCallback: (List<Request>) -> Unit, errorCallback: () -> Unit, isOwn: Boolean = false) {
        var url = URLs.URL_REQUESTS;
        if(isOwn) url = URLs.URL_OWN_REQUESTS;

        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, URLs.URL_REQUESTS,
            { response ->
                val requests = Gson().fromJson<List<Request>>(response, listOf<Request>()::class.java);
                successCallback.invoke(requests)
            },
            { error ->
                errorCallback.apply { error }
                errorCallback.invoke()
            },
        ) {
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $jwt"
                return params
            }}
        queue.add(stringRequest)
    }

    fun createRequest(successCallback: (Request) -> Unit, errorCallback: () -> Unit, request: Request) {
        var url = URLs.URL_REQUESTS;

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, URLs.URL_REQUESTS,
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
                params["Authorization"] = "Bearer $jwt"
                return params
            }

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["request"] = Gson().toJson(request)
                return params
            }
        }
        queue.add(stringRequest)
    }
}