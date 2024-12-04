package com.example.workmanager.workmanagerroomdbbasic

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    @SuppressLint("WrongThread")
    private val database =
        Room.databaseBuilder(applicationContext, ContactDatabase::class.java, "contactDB")
            .build()

    private var flag = true


    override fun doWork(): Result {
        return if(databaseUpdate()){
            Result.success()
        }else{
            Log.d("MyContactWorker", "Already sync")
            Result.failure()
        }
    }


     fun databaseUpdate(): Boolean{
         GlobalScope.launch {
             val list = database.contactDao().getContactDetails()

             try {
                 for(contact in list){
                     if(!contact.flag){
                         Log.d("MyContactWorker", "${contact.name} ${contact.phoneNo}")
                         contact.flag = true
                         flag = true
                         database.contactDao().updateContact(contact)
                     }
                 }
             }
             catch (e:Exception){
                 e.printStackTrace()
                 flag = false
             }
         }
         return flag
    }

}