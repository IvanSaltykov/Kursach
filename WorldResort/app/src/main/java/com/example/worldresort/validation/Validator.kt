package com.example.worldresort.validation

import android.content.Context
import com.example.worldresort.R

class Validator(private val context: Context) {
    fun checkPassword(userPassword: String): String? {
        return if (userPassword.length < 8)
            context.getString(R.string.error_password)
        else
            null
    }
    fun checkLogin(userEmail: String): String? {
        return if ((userEmail.length < 7) and (!userEmail.contains("@")))
            context.getString(R.string.error_email)
        else
            null
    }
    fun checkEditText(string: String): String? {
        return if (string.isEmpty())
            context.getString(R.string.error_string)
        else
            null
    }
}