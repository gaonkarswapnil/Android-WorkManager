package com.example.workmanager.workmanagernetworkapi

data class UserResponse(
    val data: Data,
    val message: String,
    val status: Int,
    val user_msg: String
)