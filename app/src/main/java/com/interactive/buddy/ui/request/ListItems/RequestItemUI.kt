package com.interactive.buddy.ui.request.ListItems

import com.interactive.buddy.data.model.request.Request

class RequestItemUI(val request:Request, val requestType:Int){
    companion object {
        const val TYPE_MY_REQUEST = 0
        const val TYPE_OPEN_REQUEST = 1
    }
}