package com.example.sportsmates.SignUp

import android.app.Activity.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignUpEmailPasswordFragmentBinding
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.signUp.data.model.User
import com.google.android.material.textfield.TextInputLayout
import java.lang.Exception
import kotlin.collections.ArrayList

class SignUpEmailFragment : Fragment() {
    private final val PICK_IMAGE_REQUEST = 22
    private val PERMISSION_CODE = 1001
   private lateinit var filePath: Uri
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
        setStepper()
        nextButton()
        onUplodedButtomSelected()
    }


    private fun validateUserInfoFiledisEmpty(textInputLayout: TextInputLayout): Boolean {
        val name = textInputLayout.editText?.text.toString()
        if (name.isEmpty()) {
            textInputLayout.error = "Field Cannot be empty"
            return false
        } else {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
            return true
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


    private fun nextButton() {

        binding.nextButton.setOnClickListener {
            if (validateUserInfoFiledisEmpty(binding.edName) && validateUserInfoFiledisEmpty(binding.edEmail) && validateUserInfoFiledisEmpty(
                    binding.edPassword
                ) && validateUserInfoFiledisEmpty(binding.edConfirmPassword) && validateConfirmPasswordAndPasswordAreTheSame()
            ) {

                replaceFragment(
                    SignUpUserInfoFragment.newInstance(forwardUserInfo()),
                    containerViewId = R.id.container
                )


            } else {
                return@setOnClickListener
            }
        }
    }


    private fun forwardUserInfo(): User {
        var user = User()
        user.name = binding.edName.editText?.text.toString()
        user.email = binding.edEmail.editText?.text.toString()
        user.password = binding.edConfirmPassword.editText?.text.toString()
        return user
    }


    private fun setStepper() {

        val stepsList = ArrayList<StepBean>()
        val stepBean0 = StepBean("1", -1)
        val stepBean1 = StepBean("2", -1)
        val stepBean2 = StepBean("3", -1)
        stepsList.add(stepBean0)
        stepsList.add(stepBean1)
        stepsList.add(stepBean2)
        binding.stepView.setStepViewTexts(stepsList)
            .setStepsViewIndicatorCompletedLineColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )
            .setStepsViewIndicatorUnCompletedLineColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.grey
                )
            )
            .setStepsViewIndicatorCompleteIcon(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.step_view_complete
                )
            )
            .setStepsViewIndicatorDefaultIcon(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.step_view_uncomplete
                )
            )

    }

    private fun onUplodedButtomSelected() {
        binding.uploadPhotoButton.setOnClickListener {
            handlePermission()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(
            Intent.createChooser(intent, "Select Image from here...."),
            PICK_IMAGE_REQUEST
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //checking request code and result code
        //if request code and result code is PICK_IMAGE_REQUEST and
        //resultCode is RESULT_OK
        //then set image in the image view

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
            data != null && data.data != null
        ) {
           filePath= data.data!!
            try {
                val inputStream=context?.contentResolver?.openInputStream(filePath)
                val selectedImage=BitmapFactory.decodeStream(inputStream)
                binding.uploadedPic.setImageBitmap(selectedImage)
                val shre=activity!!.getSharedPreferences("myPrref", Context.MODE_PRIVATE).edit()
                shre.putString("Userphoto", filePath.toString())
                shre.commit()


            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() =
            SignUpEmailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    private fun handlePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(
                    permission
                    , PERMISSION_CODE
                )
            } else {
                selectImage()
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
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage()
                } else {
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}