package com.example.sportsmates.SignUp

import android.app.Activity.*
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ProgressDialogBinding
import com.example.sportsmates.databinding.SignUpEmailPasswordFragmentBinding
import com.example.sportsmates.ext.*
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.viewmodel.SignUpViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class SignUpEmailFragment : Fragment() {

    private lateinit var filePath: Uri
    private lateinit var   progressDialog:ProgressDialog
    private var _binding: SignUpEmailPasswordFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModel()

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
        attachEventObservers()
        attachEventObservers()
        attachOnclickListeners()
    }

    private fun attachOnclickListeners() {
        binding.nextButton.setOnClickListener {
            if ( validateAllFields()){
                viewModel.onNextEmailButtonCLicked(forwardUserInfo(), filePath)
                initProgressDialog()
            }

        }
    }

    private fun attachEventObservers() {
        viewModel.signUpAuthSuccess.observe(this, Observer {
            dismissProgressDialog()
            navigateToNextScreen()

        })
        viewModel.signUpAuthFailed.observe(this, Observer { errMsg ->
            dismissProgressDialog()
            Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show()
        })
        viewModel.uploadImageFailed.observe(this, Observer { errMsg ->
            dismissProgressDialog()
            Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show()
        })

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
            Toast.makeText(activity, "Passwords don't match", Toast.LENGTH_SHORT).show()
            false

        } else {
            binding.edConfirmPassword.error = null
            binding.edConfirmPassword.isErrorEnabled = false
            true

        }
    }


    private fun validateAllFields():Boolean {
        return validateUserInfoFiledisEmpty(binding.edName) && validateUserInfoFiledisEmpty(binding.edEmail) && validateUserInfoFiledisEmpty(
            binding.edPassword
        ) && validateUserInfoFiledisEmpty(binding.edConfirmPassword) && validateConfirmPasswordAndPasswordAreTheSame()

    }

    private fun navigateToNextScreen() {
        pushFragment(
            SignUpUserInfoFragment.newInstance(forwardUserInfo()),
            containerViewId = R.id.container
        )
    }


    private fun forwardUserInfo(): User {
        val user = User()
        user.name = binding.edName.editText?.text.toString()
        user.email = binding.edEmail.editText?.text.toString()
        user.password = binding.edConfirmPassword.editText?.text.toString()
        return user
    }

    private fun onUploadedButtonSelected() {
        binding.uploadPhotoButton.setOnClickListener {
            handleStoragePermission({ selectImage(PICK_IMAGE_REQUEST) }, PERMISSION_CODE)
        }
    }


    private fun bindProfilePicture() {
        val inputStream = context?.contentResolver?.openInputStream(filePath)
        val selectedImage = BitmapFactory.decodeStream(inputStream)
        Glide.with(activity!!)
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
                //viewModel.cacheProfilePicture(filePath)
                // viewModel.onNextEmailButtonCLicked(forwardUserInfo(), filePath)
                binding.nextButton.isEnabled = true


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
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage(PICK_IMAGE_REQUEST)
                } else {
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun initProgressDialog(){
        progressDialog=ProgressDialog(activity)
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCanceledOnTouchOutside(false)
    }
    private fun dismissProgressDialog(){
        progressDialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val PICK_IMAGE_REQUEST = 22
        private const val PERMISSION_CODE = 1001
        fun newInstance() =
            SignUpEmailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


}