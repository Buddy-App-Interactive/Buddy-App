package com.interactive.buddy.data.model.Request

import java.time.LocalDateTime
import java.util.*

data class Request(
    val id : UUID,
    val CreatorName: String,
    val limit: Int,
    val requestType: RequestType,
    val TimeStamp: LocalDateTime,
)