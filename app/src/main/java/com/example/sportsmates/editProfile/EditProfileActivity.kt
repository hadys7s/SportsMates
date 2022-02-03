package com.example.sportsmates.editProfile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityEditProfile2Binding
import com.example.sportsmates.ext.*
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.fragments.SignUpEmailFragment
import com.example.sportsmates.utils.InfoType
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfile2Binding
    private val viewModel: EditProfileViewModel by viewModel()
    private lateinit var filePath: Uri
    private var user:User?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfile2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachObservers()
        loadImage()
        attachClickListeners()
        onUploadedButtonSelected()

    }
    override fun onResume() {
        super.onResume()
        fetchArguments()
    }

    private fun fetchArguments() {
        viewModel.fetchUserData()
    }
    private fun loadImage(){
        viewModel.getUserImage()
    }
    private fun attachObservers() {
        viewModel.userData.observe(this, Observer {
            bindUserData(it)
            user=it
        })
        viewModel.userImage.observe(this, Observer {
            setImage(it)
        })
        viewModel.uploadImageSuccess.observe(this, Observer {msg->
            displaySuccessToast(getString(R.string.success_toast_title),msg)
            stopShimmer(binding.shimmerViewContainer)
        })
        viewModel.uploadImageFailed.observe(this, Observer {
            displayErrorToast(getString(R.string.error_toast_title),it)
            stopShimmer(binding.shimmerViewContainer)
        })
    }

    private fun setImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .circleCrop()
            .into(binding.uploadedPic)
    }
    private fun bindUserData(userData: User?) {
        binding.nameTextField.setText(userData?.name, TextView.BufferType.EDITABLE)
        binding.mailTextField.setText(userData?.email, TextView.BufferType.EDITABLE)
        binding.cityTextField.setText(userData?.city, TextView.BufferType.EDITABLE)
        binding.passwordTextField.setText(userData?.password, TextView.BufferType.EDITABLE)
        binding.bioTextField.setText(userData?.about, TextView.BufferType.EDITABLE)
        val sports = userData?.sportsList?.joinToString(
            " , "
        )
        binding.sportsTextField.setText(sports, TextView.BufferType.EDITABLE)
    }

    private fun attachClickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.nameTextField.setOnClickListener {
            openUpdateActivityAndSendText(binding.nameBox, binding.nameTextField, InfoType.NAME)
        }
        binding.mailTextField.setOnClickListener {
            openUpdateActivityAndSendText(binding.mailBox, binding.mailTextField, InfoType.MAIL)
        }
        binding.cityTextField.setOnClickListener {
            openUpdateActivityAndSendText(binding.cityBox, binding.cityTextField, InfoType.CITY)
        }
        binding.passwordTextField.setOnClickListener {
            openUpdateActivityAndSendText(binding.passwordBox, binding.passwordTextField, InfoType.PASSWORD)
        }
        binding.bioTextField.setOnClickListener {
            openUpdateActivityAndSendText(binding.bioBox, binding.bioTextField, InfoType.BIO)
        }
        binding.sportsTextField.setOnClickListener {
            openUpdateActivityAndSendText(binding.sportsBox, binding.sportsTextField, InfoType.SPORTS)
        }
    }

    private fun openUpdateActivityAndSendText(
        hint: TextInputLayout,
        oldData: TextInputEditText,
        infoType: InfoType
    ) {
            when (infoType) {
                InfoType.SPORTS -> {
                    UpdateUserInfoActivity.start(this,infoType = InfoType.SPORTS.toString())
                }
                InfoType.CITY -> {
                    UpdateUserInfoActivity.start(this,infoType = InfoType.CITY.toString())
                }
                else -> {
                    UpdateUserInfoActivity.start(this,oldValue = oldData.text.toString(),oldPassword = user?.password!!,hint = hint.hint.toString())
                }
            }

    }

    private fun onUploadedButtonSelected() {
        binding.uploadPhotoButton.setOnClickListener {
            handleStoragePermission(
                { selectImage(SignUpEmailFragment.PICK_IMAGE_REQUEST) },
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
        viewModel.uploadProfileImage(filePath)
        startShimmer()
    }
    private fun startShimmer(){
        binding.shimmerViewContainer.visibility=View.VISIBLE
        binding.shimmerViewContainer.startShimmer()
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
                    displayInfoToast(getString(R.string.info_toast_title), getString(R.string.permissin_denied))
                }
            }
        }

    }


}