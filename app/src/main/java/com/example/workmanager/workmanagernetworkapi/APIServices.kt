package com.example.workmanager.workmanagernetworkapi

import org.jetbrains.annotations.TestOnly
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIServices {
    @FormUrlEncoded
    @POST("users/register")
    suspend fun createUser(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String,
        @Field("gender") gender: String,
        @Field("phone_no") phoneNo: String
    ): Response<UserResponse>
}