package com.example.workmanager.workmanagernetworkapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("select * from userTable")
    suspend fun getUserDetails(): List<User>

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

}