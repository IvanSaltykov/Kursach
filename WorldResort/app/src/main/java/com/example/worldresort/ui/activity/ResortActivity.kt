package com.example.worldresort.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.worldresort.R
import com.example.worldresort.databinding.ActivityResortBinding
import com.example.worldresort.preferences.PreferencesManager
import com.example.worldresort.ui.fragment.*

class ResortActivity : AppCompatActivity() {
    lateinit var binding: ActivityResortBinding
    lateinit var preferences: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResortBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = PreferencesManager(this)
        binding.toolbar.title = "Курорты"
        binding.bottomNavigationViewResort.selectedItemId = R.id.itemResortBottomNavigationMenu
        binding.bottomNavigationViewResort.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.itemResortBottomNavigationMenu -> {
                    openFragment(WorldFragment())
                    binding.toolbar.title = "Курорты"
                }
                R.id.itemFavouritesBottomNavigationMenu -> {
                    openFragment(FavouritesFragment())
                    binding.toolbar.title = "Избранное"
                }
                R.id.itemProfileBottomNavigationMenu -> {
                    openFragment(ProfileFragment())
                    binding.toolbar.title = "Профиль"
                }
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerResort, fragment)
            .addToBackStack("backStack")
            .commit()
    }
}