package com.example.workmanager.workmanagernetworkapi

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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.workmanager.R
import com.example.workmanager.databinding.ActivityUserRegistrationBinding
import kotlinx.coroutines.launch

class UserRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserRegistrationBinding
    private lateinit var database: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "userDB"
        ).build()


        binding.btnRegisterScreenButton.setOnClickListener {
            val firstName = binding.etRegisterScreenFirstName.text.toString()
            val lastName = binding.etRegisterScreenLastName.text.toString()
            val email = binding.etRegisterScreenEmail.text.toString()
            val password = binding.etRegisterScreenPassword.text.toString()
            val confirmPassword = binding.etRegisterScreenConfirmPassword.text.toString()
            val gender = when (binding.rbRegisterGender.checkedRadioButtonId) {
                R.id.rbRegisterScreenMale -> "Male"
                R.id.rbRegisterScreenFemale -> "Female"
                else -> {}
            }.toString()
            val phone = binding.etRegisterScreenPhone.text.toString().toLong()

            val user = User(
                first_name = firstName,
                last_name = lastName,
                email = email,
                password = password,
                confirm_password = confirmPassword,
                gender = gender,
                phone_no = phone
            )


            lifecycleScope.launch {
                database.userDao().insertUser(user)
            }

        }

        binding.btnSync.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequest.Builder(MyUserWorker::class.java)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)
        }

    }
}