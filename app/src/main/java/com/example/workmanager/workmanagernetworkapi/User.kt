package com.example.workmanager.workmanagernetworkapi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val confirm_password: String,
    val email: String,
    val first_name: String,
    val gender: String,
    val last_name: String,
    val password: String,
    val phone_no: Long,

    var flag: Boolean = false
)