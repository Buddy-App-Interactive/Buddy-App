package com.interactive.buddy.data

object URLs {
    private const val ROOT_URL = "http://192.168.0.106:8000/"
    const val URL_REGISTER = ROOT_URL + "register"
    const val URL_LOGIN = ROOT_URL + "login"
    const val URL_REQUESTS = ROOT_URL + "requests"
    const val URL_OWN_REQUESTS = "$URL_REQUESTS/own"
    const val URL_CHATS = ROOT_URL + "chats"
    const val URL_MESSAGE_SOCKET = ROOT_URL + "socket/messages"
    const val URL_MESSAGES = ROOT_URL + "messages"
}