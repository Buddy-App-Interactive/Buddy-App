package com.interactive.buddy.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    var username: String,
    var email: String?,
    val key: String?,
    val jwt: String,
    var mood: Int
){
}