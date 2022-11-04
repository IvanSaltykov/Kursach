package com.example.worldresort.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.worldresort.R
import com.example.worldresort.database.DataBaseManager
import com.example.worldresort.databinding.ActivityAuthorizationBinding
import com.example.worldresort.model.User
import com.example.worldresort.preferences.PreferencesManager
import com.example.worldresort.validation.Validator

class AuthorizationActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthorizationBinding
    lateinit var validator: Validator
    lateinit var database: DataBaseManager
    lateinit var preferences: PreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validator = Validator(this)
        database = DataBaseManager(this)
        preferences = PreferencesManager(this)
        binding.buttonRegistAuth.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.buttonAuth.setOnClickListener {
            authorizationUser()
        }
    }
    fun authorizationUser() {
        val login = binding.editTextLoginAuth.text.toString().trim()
        val password = binding.editTextPasswordAuth.text.toString().trim()
        binding.inputLayoutLoginAuth.error = validator.checkLogin(login)
        binding.inputLayoutPasswordAuth.error = validator.checkPassword(password)
        if (binding.inputLayoutLoginAuth.error == null &&
                binding.inputLayoutPasswordAuth.error == null
        ) {
            val account = database.getAccountAuth(login)
            if (account != null)
                if (account.password == password) {
                    preferences.signInUser(database.getUser(account.id) ?: User(account.id, "Пусто", "Пусто"))
                    startActivity(Intent(this@AuthorizationActivity, ResortActivity::class.java))
                }
                else
                    binding.inputLayoutPasswordAuth.error = getString(R.string.error_auth_password)
            else
                binding.inputLayoutLoginAuth.error = getString(R.string.error_auth_login)
        }
    }
}