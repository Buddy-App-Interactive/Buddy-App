package com.interactive.buddy.data.model.Request

import java.time.LocalDateTime
import java.util.*

data class Request(
    val id : UUID,
    val id_creator: UUID,
    val description: String,
    val type: RequestType,
    val limit: Int,
    val timeframe: LocalDateTime,
)