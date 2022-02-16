package com.example.sportsmates.auth.presentation.signUp.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignUpEmailPasswordFragmentBinding
import com.example.sportsmates.ext.*
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.presentation.signUp.viewmodel.SignUpSteps
import com.example.sportsmates.auth.presentation.signUp.viewmodel.SignUpViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SignUpEmailFragment : Fragment() {
    private val viewModel: SignUpViewModel by sharedViewModel()
    private lateinit var filePath: Uri
    private var validateImage: Boolean = false
    private lateinit var dialog: AlertDialog
    private var _binding: SignUpEmailPasswordFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpEmailPasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStepper(-1, -1, -1, binding.stepView)
        onUploadedButtonSelected()
        attachOnclickListeners()
        setInfoFromInput()
    }

    private fun attachOnclickListeners() {
        binding.nextButton.setOnClickListener {
            if (validateAllFields()) {
                if (!validateImage) {
                    displayWarningToast("Warning ", "Select a Photo !")
                } else {
                    viewModel.initInput(user = forwardUserInfo(),SignUpSteps.STEP_ONE)
                    navigateToNextScreen()
                }
            }

        }
    }

    private fun validateUserInfoFiledisEmpty(textInputLayout: TextInputLayout): Boolean {
        val name = textInputLayout.editText?.text.toString()
        return if (name.isEmpty()) {
            textInputLayout.error = "Field Cannot be empty"
            false
        } else {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun validateConfirmPasswordAndPasswordAreTheSame(): Boolean {
        val confirmPassword = binding.edConfirmPassword.editText?.text.toString()
        val password = binding.edPassword.editText?.text.toString()
        return if (!confirmPassword.contentEquals(password)) {
            displayWarningToast("Warning ", "Password don't match")
            false

        } else {
            binding.edConfirmPassword.error = null
            binding.edConfirmPassword.isErrorEnabled = false
            true

        }
    }


    private fun validateAllFields(): Boolean {
        return validateUserInfoFiledisEmpty(binding.edName) && validateUserInfoFiledisEmpty(binding.edEmail) && validateUserInfoFiledisEmpty(
            binding.edPassword
        ) && validateUserInfoFiledisEmpty(binding.edConfirmPassword) && validateConfirmPasswordAndPasswordAreTheSame()

    }

    private fun navigateToNextScreen() {
        pushFragment(
            SignUpUserInfoFragment.newInstance(),
            containerViewId = R.id.container
        )
    }
    private fun setInfoFromInput(){
       val info = viewModel.getInfo()
        binding.edName.editText?.setText(info.name)
        binding.edEmail.editText?.setText(info.email)
        binding.edPassword.editText?.setText(info.password)
        binding.edConfirmPassword.editText?.setText(info.password)
        if (info.userImage != null)
        Glide.with(requireActivity())
            .load(info.userImage)
            .circleCrop()
            .into(binding.uploadedPic)
    }

    private fun forwardUserInfo(): User {
        val user = User()
        user.name = binding.edName.editText?.text.toString()
        user.email = binding.edEmail.editText?.text.toString()
        user.password = binding.edConfirmPassword.editText?.text.toString()
        user.userImage = filePath
        return user
    }


    private fun bindProfilePicture() {
        val inputStream = context?.contentResolver?.openInputStream(filePath)
        val selectedImage = BitmapFactory.decodeStream(inputStream)
        validateImage = true
        Glide.with(requireActivity())
            .load(selectedImage)
            .circleCrop()
            .into(binding.uploadedPic)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
            data != null && data.data != null
        ) {
            filePath = data.data!!

            try {
                bindProfilePicture()
                binding.nextButton.isEnabled = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun onUploadedButtonSelected() {
        binding.uploadPhotoButton.setOnClickListener {
            handleStoragePermission({ selectImage(PICK_IMAGE_REQUEST) }, PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage(PICK_IMAGE_REQUEST)
                } else {
                    displayInfoToast("info", "Permission Denied")
                }
            }
        }

    }

    private fun showLoading() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        val inflater: LayoutInflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(R.layout.progress_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun hideLoading() {
        dialog.dismiss()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val PICK_IMAGE_REQUEST = 22
        const val PERMISSION_CODE = 1001
        fun newInstance() =
            SignUpEmailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


}