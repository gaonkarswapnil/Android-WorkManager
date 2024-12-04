package com.example.workmanager.workerresultype

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanager.R
import com.example.workmanager.databinding.ActivityWorkerResultTypeBinding

class WorkerResultTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkerResultTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWorkerResultTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresStorageNotLow(true)
                .build()

            val inputData = Data.Builder()
                .putInt("Key_One", binding.etFirstNumber.text.toString().toInt())
                .putInt("Key_Two", binding.etSecondNumber.text.toString().toInt())
                .build()

            val workerRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build()

            WorkManager.getInstance(this).enqueue(workerRequest)

            WorkManager.getInstance(this).getWorkInfoByIdLiveData(workerRequest.id).observe(this){workInfo ->
                if(workInfo!=null && workInfo.state.isFinished){
                    if(workInfo.state == WorkInfo.State.SUCCEEDED){
                        val output = workInfo.outputData
                        val number = output.getInt("NumDiv", 0)
                        val bool = output.getBoolean("BoolFlag", false)

                        Log.d("Inside Activity", "Result $number  $bool")
                    }else if (workInfo.state == WorkInfo.State.FAILED){
                        Log.d("Inside Activity", "Worked Failed")
                    }else if(workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING){
                        Log.d("Inside Activity", "Work Still Running")
                    }
                }
            }
        }



    }
}