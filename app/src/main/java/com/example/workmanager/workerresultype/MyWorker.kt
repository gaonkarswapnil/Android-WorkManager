package com.example.workmanager.workerresultype

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
   lateinit var output: Data
    override fun doWork(): Result {
        var div = 0;
        var flag = false
        try {
            val input = inputData
            val one = input.getInt("Key_One", 0)
            val two = input.getInt("Key_Two", 0)

            // Avoid division by zero
            if (two == 0) {
                throw ArithmeticException("Division by zero")
            }

            div = one / two
            Log.d("MyWorker", "Result: $div")
            flag = true

            output = Data.Builder()
                .putInt("NumDiv", div)
                .putBoolean("BoolFlag", flag)
                .build()
        }catch (e: Exception){
            e.printStackTrace()
            return Result.failure()
        }
        finally {
            return if(flag)
                Result.success(output)
            else
                Result.retry()
        }
    }
}