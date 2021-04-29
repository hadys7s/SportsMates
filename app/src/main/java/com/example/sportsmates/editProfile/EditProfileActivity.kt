package com.example.sportsmates.editProfile
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityEditProfile2Binding
import com.example.sportsmates.ext.*
import com.example.sportsmates.home.MainActivity
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.fragments.SignUpEmailFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfile2Binding
    private val viewModel:EditProfileViewModel by viewModel()
    private lateinit var filePath: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditProfile2Binding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListeners()
        onUploadedButtonSelected()
        fetchArguments()
        attachObservers()
    }
    private fun fetchArguments() {
        viewModel.fetchUserData()
        viewModel.getUserImage()
    }
    private fun attachObservers(){
        viewModel.userData.observe(this, Observer {
            bindUserData(it)
        })
        viewModel.userImage.observe(this, Observer {
            setImage(it)
        })
    }
    private fun setImage(uri: Uri){
        Glide.with(this)
            .load(uri)
            .circleCrop()
            .into(binding.uploadedPic)
    }
    private fun bindUserData(userData: User?){
        binding.name.setText(userData!!.name,TextView.BufferType.EDITABLE)
        binding.mail.setText(userData.email,TextView.BufferType.EDITABLE)
        binding.city.setText(userData.city,TextView.BufferType.EDITABLE)
        binding.password.setText(userData.password,TextView.BufferType.EDITABLE)
        val sports=userData.sportsList!!.joinToString(
            " , "
        )
        binding.sports.setText(sports,TextView.BufferType.EDITABLE)
    }
    private fun attachClickListeners() {
        binding.backBtn.setOnClickListener {
            openTopActivity(this,MainActivity())
        }
        binding.name.setOnClickListener {
          openUpdateActivityAndSendText(binding.nameBox,binding.name)
        }
        binding.mail.setOnClickListener {
          openUpdateActivityAndSendText(binding.mailBox,binding.mail)
        }
        binding.city.setOnClickListener {
          openUpdateActivityAndSendText(binding.cityBox,binding.city)
        }
        binding.password.setOnClickListener {
          openUpdateActivityAndSendText(binding.passwordBox,binding.password)
        }
        binding.bio.setOnClickListener {
          openUpdateActivityAndSendText(binding.bioBox,binding.bio)
        }
        binding.sports.setOnClickListener {
          openUpdateActivityAndSendText(binding.sportsBox,binding.sports)
        }
    }
    private fun openUpdateActivityAndSendText(textInputLayout: TextInputLayout,text: TextInputEditText){
        val intent=Intent(this,UpdateUserInfoActivity::class.java).apply {
            putExtra("Edit",text.text.toString())
            putExtra("Hint",textInputLayout.hint.toString())
        }
        startActivity(intent)
    }
    private fun onUploadedButtonSelected() {
        binding.uploadPhotoButton.setOnClickListener {
            handleStoragePermission({ selectImage(SignUpEmailFragment.PICK_IMAGE_REQUEST) },
                SignUpEmailFragment.PERMISSION_CODE
            )
        }
    }
    private fun bindProfilePicture() {
        val inputStream = this?.contentResolver?.openInputStream(filePath)
        val selectedImage = BitmapFactory.decodeStream(inputStream)
        Glide.with(this)
            .load(selectedImage)
            .circleCrop()
            .into(binding.uploadedPic)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SignUpEmailFragment.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            filePath = data.data!!
            try {
                bindProfilePicture()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            SignUpEmailFragment.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage(SignUpEmailFragment.PICK_IMAGE_REQUEST)
                } else {
                    displayInfoToast("info","Permission Denied")
                }
            }
        }

    }


}