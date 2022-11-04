package com.example.worldresort.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.worldresort.R
import com.example.worldresort.data.Resorts
import com.example.worldresort.database.DataBaseManager
import com.example.worldresort.preferences.PreferencesManager

class SplashActivity : AppCompatActivity() {
    lateinit var dataBase: DataBaseManager
    lateinit var resorts: Resorts
    lateinit var preferences: PreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        dataBase = DataBaseManager(this)
        preferences = PreferencesManager(this)
        resorts = Resorts()
        if (preferences.launch) {
            resorts.dataResortList().forEach {
                dataBase.insertResort(it)
            }
            preferences.launch = false
        }
        Handler().postDelayed(
            {
                startActivity(Intent(this, ResortActivity::class.java))
                finish()
            }, 3000
        )
    }
}