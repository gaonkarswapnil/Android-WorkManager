package com.example.workmanager.workmanagernetworkapi

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MyUserWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    val database = Room.databaseBuilder(
        applicationContext,
        UserDatabase::class.java,
        "userDB"
    ).build()

    val retrofitHelper = RetrofitHelper.getInstance()
    private var flag: Boolean = false

    override fun doWork(): Result {
        return if(addUserToServer()){
            Log.d("MyUserWorker", "SUCCESS   ${Result.success()}" )
            Result.success()
        }else{
            Log.d("MyUserWorker", "failure   ${Result.failure()}" )
            Result.failure()
        }
    }

    fun addUserToServer(): Boolean{
        runBlocking {
            val userList = database.userDao().getUserDetails()

            for(user in userList){
                if(!user.flag){
                    Log.d("MyUserWorker", "${user.first_name} ${user.last_name}")

                    val list = retrofitHelper.createUser(
                        firstName =user.first_name,
                        lastName = user.last_name,
                        email = user.email,
                        password = user.password,
                        confirmPassword = user.confirm_password,
                        gender = user.gender,
                        phoneNo = user.phone_no.toString()
                    )

                    Log.d("MyUserWorker", "${list.body()}")
                    user.flag = true
                    database.userDao().updateUser(user)
                    flag = true
                }
            }
        }
        return flag
    }

}