package com.interactive.buddy.data.model

import java.util.*

class Chat constructor(
    val id: UUID,
    val userFromId: UUID,
    val userToId: UUID,
    val requestId: UUID,
    ) {
}