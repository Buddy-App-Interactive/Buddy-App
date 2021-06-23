package com.interactive.buddy.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(private var mCtx: Context) {
    private var mRequestQueue: RequestQueue?

    // getApplicationContext() is key, it keeps you from leaking the
    // Activity or BroadcastReceiver if someone passes one in.
    val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                mRequestQueue = Volley.newRequestQueue(mCtx.applicationContext)
            }
            return mRequestQueue
        }

    fun <T> addToRequestQueue(req: Request<T>?) {
        requestQueue!!.add(req)
    }

    companion object {
        private var mInstance: VolleySingleton? = null
        @Synchronized
        fun getInstance(context: Context): VolleySingleton {
            if (mInstance == null) {
                mInstance = VolleySingleton(context)
            }
            return mInstance!!
        }
    }

    init {
        mRequestQueue = requestQueue
    }
}