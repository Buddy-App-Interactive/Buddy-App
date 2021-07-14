package com.interactive.buddy.data.model.request

import com.interactive.buddy.data.model.LoggedInUser
import com.interactive.buddy.data.model.User
import java.time.LocalDateTime
import java.util.*

data class Request(
    val _id : String,
    val creator: User,
    val description: String,
    val type: RequestType,
    val limit: Int,
    val endDate: Date,
)