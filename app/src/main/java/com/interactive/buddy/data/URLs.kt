package com.interactive.buddy.data

object URLs {
    private const val ROOT_URL = "http://192.168.8.101:8000/"
    const val URL_REGISTER = ROOT_URL + "register"
    const val URL_LOGIN = ROOT_URL + "login"
    const val URL_REQUESTS = ROOT_URL + "requests"
    const val URL_OWN_REQUESTS = "$URL_REQUESTS/own"
}