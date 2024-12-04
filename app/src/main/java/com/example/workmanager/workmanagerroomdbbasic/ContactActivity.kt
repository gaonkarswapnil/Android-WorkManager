package com.example.workmanager.workmanagerroomdbbasic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor
import com.example.workmanager.R
import com.example.workmanager.databinding.ActivityContactBinding
import kotlinx.coroutines.launch

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding
    lateinit var database: ContactDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database =
            Room.databaseBuilder(applicationContext, ContactDatabase::class.java, "contactDB")
                .build()


//        val contact1 = Contact(0, "Oliver", "09132141314")
//        val contact2 = Contact(0, "Swapnil", "09132141314")

        val contact = Contact(0, "Mayur", "9876543210")

        lifecycleScope.launch {
            database.contactDao().insertContact(contact)
//            database.contactDao().insertContact(contact2)
        }


        binding.btnSync.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)
        }
    }
}