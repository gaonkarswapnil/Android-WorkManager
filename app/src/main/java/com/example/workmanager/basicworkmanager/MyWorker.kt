package com.example.workmanager.basicworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.d("MyWorker", "Work is being done")

        try {
            Thread.sleep(2000)
        }catch (e: InterruptedException){
            e.printStackTrace()
        }
        return Result.success()
    }

}