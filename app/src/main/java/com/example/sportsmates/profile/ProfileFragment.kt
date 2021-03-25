package com.example.sportsmates.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sportsmates.SignUp.SignInFragment
import com.example.sportsmates.databinding.ProfileFragmentBinding
import com.example.sportsmates.ext.openTopActivity
import com.example.sportsmates.signUp.SignUpActivity
import com.example.sportsmates.signUp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModel()

    private var _binding: ProfileFragmentBinding? = null


    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchArguments()
        attachEventObservers()
        attachCLickListeners()

    }

    private fun fetchArguments() {
        viewModel.fetchUserData()
    }

    private fun attachEventObservers() {
        viewModel.userData.observe(this, Observer { userData ->
          //  Toast.makeText(activity, userData?.name, Toast.LENGTH_LONG).show()
            bindUserData(userData)

        })
    }

    private fun attachCLickListeners() {
        binding.editProfileButton.setOnClickListener {

        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            openTopActivity(activity, SignUpActivity())
        }
    }

    private fun bindUserData(userInfo: User?) {
        binding.profileName.text = userInfo?.name
        // binding.profileImage = userInfo.name
        //   binding.profilePhoneNumber.text = userInfo?.phoneNumber
        binding.profileEmail.text = userInfo?.email
        binding.profileAddress.text = userInfo?.city
        //     binding.ProfileAboutMeDescription.text = userInfo?.phoneNumber
        binding.sports1.text = userInfo?.sportsList?.get(0)
        if (userInfo?.sportsList?.size!! > 1) {
            binding.sports2.text = userInfo?.sportsList?.get(1)
            binding.sports2.isVisible = true

            if (userInfo?.sportsList?.size!! > 2) {
                binding.sports3.text = userInfo?.sportsList?.get(2)
                binding.sports3.isVisible = true

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val USER_ID = "userId"


        fun newInstance(userID: String?) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userID)
                }
            }
    }


}