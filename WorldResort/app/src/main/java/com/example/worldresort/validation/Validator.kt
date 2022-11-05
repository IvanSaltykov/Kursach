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
    fun checkPassportNumber(number: String): String? {
        return if (number.isEmpty())
            context.getString(R.string.error_string)
        else
            if (Integer.parseInt(number) in 1000..9999)
                null
            else
                context.getString(R.string.error_passport_number)
    }
    fun checkPassportSeries(series: String): String? {
        return if (series.isEmpty())
            context.getString(R.string.error_string)
        else
            if (Integer.parseInt(series) in 100000..999999)
                null
            else
                context.getString(R.string.error_passport_series)
    }
    fun checkAge(age: String): String? {
        return if (age.isEmpty())
            context.getString(R.string.error_string)
        else
            if (Integer.parseInt(age) < 18)
                context.getString(R.string.error_age)
            else
                null
    }
}