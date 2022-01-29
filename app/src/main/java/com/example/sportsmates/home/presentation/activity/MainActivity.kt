package com.example.sportsmates.home.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sportsmates.R
import com.example.sportsmates.chat.ChatActivity
import com.example.sportsmates.chatbot.ChatBotActivity
import com.example.sportsmates.coach.CoachFragment
import com.example.sportsmates.databinding.ActivityMainBinding
import com.example.sportsmates.discover.ContactsFragment
import com.example.sportsmates.ext.getCurrentUserID
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.home.presentation.fragment.HomeFragment
import com.example.sportsmates.place.PlaceFragment
import com.example.sportsmates.profile.ProfileFragment
import io.kommunicate.KmConversationBuilder
import io.kommunicate.callbacks.KmCallback
import io.kommunicate.users.KMUser


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var userUid: String? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.chat_icon_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.chat->{
                val intent=Intent(this,ChatActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        replaceFragment(HomeFragment.newInstance(), containerViewId = R.id.main_container_view)
        bottomNavigationController()
        binding.chatBot.setOnClickListener {
            openChatBotConnection()
        }
    }

    private fun bottomNavigationController() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Respond to navigation item 1 reselection
                    replaceFragment(
                        HomeFragment.newInstance(),
                        containerViewId = R.id.main_container_view
                    )
                    true
                }
                R.id.places -> {
                    // Respond to navigation item 2 reselection
                    replaceFragment(
                        PlaceFragment.newInstance(),
                        containerViewId = R.id.main_container_view
                    )
                    true
                }
                R.id.discover -> {
                    replaceFragment(
                        ContactsFragment.newInstance(),
                        containerViewId = R.id.main_container_view
                    )
                    true
                }
                R.id.coach -> {
                    // Respond to navigation item 2 reselection
                    replaceFragment(
                        CoachFragment.newInstance(),
                        containerViewId = R.id.main_container_view
                    )

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


    }

    private fun openChatBotConnection() {
        val intent=Intent(this,ChatBotActivity::class.java)
        startActivity(intent)
    }

    private fun addFragment(fragment: Fragment) {
        val fragmentTransiction = supportFragmentManager.beginTransaction()
        fragmentTransiction.add(R.id.main_container_view, fragment)
            .addToBackStack(Fragment::class.java.simpleName).commit()
    }


    companion object {
        private const val USER_ID = "userId"
    }

}
