package com.example.workmanager.workmanagernetworkapi

data class UserRequest(
    val confirm_password: String,
    val email: String,
    val first_name: String,
    val gender: String,
    val last_name: String,
    val password: String,
    val phone_no: Long
)