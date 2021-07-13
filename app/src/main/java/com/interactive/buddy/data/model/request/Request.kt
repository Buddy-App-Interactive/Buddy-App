package com.interactive.buddy.data.model.request

import java.time.LocalDateTime
import java.util.*

data class Request(
    val _id : String,
    val id_creator: String,
    val description: String,
    val type: RequestType,
    val limit: Int,
    val endDate: Date,
)