package com.interactive.buddy.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.interactive.buddy.data.model.Request.Request
import com.interactive.buddy.data.model.Request.RequestType
import java.time.LocalDateTime
import java.util.*

class RequestStore {
    @RequiresApi(Build.VERSION_CODES.O)
    companion object {
        fun getRequests() : List<Request> = listOf<Request>(
            Request(UUID.randomUUID(), "Thomas", 5, RequestType.HAPPY, LocalDateTime.now()),
            Request(UUID.randomUUID(), "Heinz", 1, RequestType.DEPRESSED, LocalDateTime.now()),
            Request(UUID.randomUUID(), "Lisa", 10, RequestType.JUSTTALK, LocalDateTime.now()),
            Request(UUID.randomUUID(), "Katharina", 3, RequestType.BORED, LocalDateTime.now()),
            Request(UUID.randomUUID(), "Christina", 2, RequestType.HAPPY, LocalDateTime.now()),
        )
    }
}