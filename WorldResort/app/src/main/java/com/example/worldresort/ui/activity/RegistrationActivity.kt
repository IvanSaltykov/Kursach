package com.example.worldresort.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.worldresort.database.DataBaseManager
import com.example.worldresort.databinding.ActivityRegistrationBinding
import com.example.worldresort.model.Account
import com.example.worldresort.model.User
import com.example.worldresort.validation.Validator

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    lateinit var validator: Validator
    lateinit var database: DataBaseManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validator = Validator(this)
        database = DataBaseManager(this)
        binding.buttonRegistration.setOnClickListener {
            registrationAccount()
        }
        binding.buttonBackRegistr.setOnClickListener {
            finish()
        }
    }
    private fun registrationAccount() {
        val login = binding.editTextLoginRegistration.text.toString().trim()
        val password = binding.editTextPasswordRegistration.text.toString().trim()
        val name = binding.editTextNameRegistration.text.toString().trim()
        val surname = binding.editTextSurnameRegistration.text.toString().trim()
        binding.inputLayoutNameRegistration.error = validator.checkEditText(name)
        binding.inputLayoutSurnameRegistration.error = validator.checkEditText(surname)
        binding.inputLayoutLoginRegistration.error = validator.checkLogin(login)
        binding.inputLayoutPasswordRegistration.error = validator.checkPassword(password)
        if (binding.inputLayoutNameRegistration.error == null &&
            binding.inputLayoutSurnameRegistration.error == null &&
            binding.inputLayoutLoginRegistration.error == null &&
            binding.inputLayoutPasswordRegistration.error == null
        ) {
            val accountId = (100000..999999).random()
            database.insertAccount(
                Account(accountId, login, password)
            )
            database.insertUser(
                User(
                    accountId,
                    name,
                    surname
                )
            )
            finish()
        }
    }
}