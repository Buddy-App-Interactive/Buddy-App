package com.interactive.buddy.data.model.request

import com.interactive.buddy.data.model.User
import java.util.*

data class Request(
    val _id : String,
    val creator: User,
    val description: String,
    val type: RequestType,
    val limit: Int,
    var language: String,
    val endDate: Date,
)