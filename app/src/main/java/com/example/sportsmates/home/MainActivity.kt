package com.example.sportsmates.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityMainBinding
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.profile.ProfileFragment



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var userUid: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bottomNavigationController()


        binding.chatBot.setOnClickListener {

        }

    }


    private fun bottomNavigationController() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Respond to navigation item 1 reselection
                    true
                }
                R.id.places -> {
                    // Respond to navigation item 2 reselection
                    true
                }
                R.id.discover -> {
                    // Respond to navigation item 2 reselection
                    true
                }
                R.id.coach -> {
                    // Respond to navigation item 2 reselection
                    true
                }
                R.id.profile -> {
                    replaceFragment(
                        fragment = ProfileFragment.newInstance(userUid),
                        containerViewId = R.id.main_container_view
                    )
                    true

                }
                else -> false
            }
        }

        binding.bottomNavigation.setOnNavigationItemReselectedListener() {

        }


    }

    companion object {
        private const val USER_ID = "userId"
    }


}