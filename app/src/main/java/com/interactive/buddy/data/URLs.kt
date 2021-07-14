package com.interactive.buddy.data

object URLs {
    private const val ROOT_URL = "http://192.168.0.106:8000/"
    const val URL_REGISTER = ROOT_URL + "register"
    const val URL_LOGIN = ROOT_URL + "login"
    const val URL_REQUESTS = ROOT_URL + "requests"
    const val URL_OWN_REQUESTS = "$URL_REQUESTS/own"
    const val URL_CHATS = ROOT_URL + "chats"
    const val URL_MESSAGE_SOCKET = ROOT_URL
    const val URL_MESSAGES = ROOT_URL + "messages"
    const val URL_SEND_MESSAGE = ROOT_URL + "message/send"
    const val URL_MOODS = ROOT_URL + "moods"
    const val URL_USERDATA = ROOT_URL + "user"
    const val URL_PASSWORD = ROOT_URL + "password"
}