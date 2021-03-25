package com.example.sportsmates.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityMainBinding
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.login.SignInViewModel
import com.example.sportsmates.profile.ProfileFragment
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: SignInViewModel by viewModel()
    var userUid : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bottomNavigationController()
        binding.chatBot.setOnClickListener {

        }
        viewModel.login("hadyhessen.hh@gmail.com","Hady123456")

            viewModel.loginSuccess.observe(this, Observer { userId ->
                //  redirect home
                userUid = userId

                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()

            })
            viewModel.loginFailed.observe(this, Observer { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            })


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


}