package com.example.sportsmates.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityMainBinding
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.profile.ProfileFragment
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //  val viewModel: SignInViewModel by viewModel()
    var userUid: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bottomNavigationController()
        val preferences=getSharedPreferences("myPrref", Context.MODE_PRIVATE)
        val imagePath=preferences.getString("Userphoto","a7a")
        Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show()
        val imageUri= imagePath?.let { convertStringTouUri(it) }
        imageUri?.let { uploadPhoto(it) }
        binding.chatBot.setOnClickListener {

        }
        //viewModel.login("hadyhessen.hh@gmail.com","Hady123456")

        /*  viewModel.loginSuccess.observe(this, Observer { userId ->
              //  redirect home
              userUid = userId

              Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()

          })
          viewModel.loginFailed.observe(this, Observer { errorMessage ->
              Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
          })*/


    }
    private fun convertStringTouUri(filepath: String):Uri{
        val imagepathUri=Uri.parse(filepath)
        return imagepathUri
    }
    private fun uploadPhoto(filepath:Uri){
        if (filepath!=null){
            val storageReference=FirebaseStorage.getInstance().reference.child("images/"+UUID.randomUUID().toString())
            storageReference.putFile(filepath)
                .addOnSuccessListener {
                    Toast.makeText(this, "image uploaded", Toast.LENGTH_SHORT).show()
                }
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