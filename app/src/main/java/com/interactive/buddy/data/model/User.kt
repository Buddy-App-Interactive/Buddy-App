package com.interactive.buddy.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val _id: String,
    var username: String? = null,
){
}