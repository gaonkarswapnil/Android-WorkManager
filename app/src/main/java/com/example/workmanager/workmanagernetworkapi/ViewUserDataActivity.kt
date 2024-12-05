package com.example.workmanager.workmanagernetworkapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.workmanager.R
import com.example.workmanager.databinding.ActivityViewUserDataBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewUserDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewUserDataBinding
    private lateinit var database: UserDatabase
    private lateinit var adapter: UserAdapter
    private var user: List<User> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "userDB"
        ).build()

        runBlocking {
            user = database.userDao().getUserDetails()
        }

        binding.rvRecyclerData.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(user)
        binding.rvRecyclerData.adapter = adapter



        binding.btnSync.setOnClickListener{
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequest.Builder(MyUserWorker::class.java)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)


//            Intent(this, ViewUserDataActivity::class.java).also{
//                startActivity(it)
//            }
            val intent = getIntent()
            finish()
            startActivity(intent)
        }
    }
}