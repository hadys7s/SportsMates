package com.example.sportsmates.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.presentation.signUp.SignUpActivity
import com.example.sportsmates.databinding.ProfileFragmentBinding
import com.example.sportsmates.editProfile.EditProfileActivity
import com.example.sportsmates.ext.*
import com.example.sportsmates.networking.Resource
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()

    private var _binding: ProfileFragmentBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        attachEventObservers()
        attachCLickListeners()
    }

    private fun fetchArguments() {
        viewModel.fetchUserData()
        //viewModel.getUserImage()
    }

    override fun onResume() {
        super.onResume()
        fetchArguments()
    }


    private fun attachEventObservers() {
        stateCollector(viewModel.userData) {
            when (it) {
                is Resource.Error -> {
                    stopShimmer(binding.shimmerViewContainer)
                    displayErrorToast("Failed",it.exception.message!!)
                }
                is Resource.Success -> {
                    stopShimmer(binding.shimmerViewContainer)
                    bindUserData(it.data)
                    it.data.userImage?.let { it1 -> setUserImage(it1) }
                }
            }
        }
    }

    private fun attachCLickListeners() {
        binding.editProfileButton.setOnClickListener {
            openActivity(EditProfileActivity(), withTransitionAnimation(binding.profileImage))
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            openTopActivity(requireActivity(), SignUpActivity())
        }
    }

    private fun setUserImage(uri: Uri) {
        Glide.with(requireActivity())
            .load(uri)
            .circleCrop()
            .into(binding.profileImage)
    }

    private fun bindUserData(userInfo: User) {
        binding.profileName.text = userInfo.name
        binding.profileEmail.text = userInfo.email
        binding.profileAddress.text = userInfo.city
        binding.ProfileAboutMeDescription.text = userInfo.about
        binding.sports1.text = userInfo.sportsList.get(0)
        if (userInfo.sportsList.size > 1) {
            binding.sports2.text = userInfo.sportsList[1]
            binding.sports2.isVisible = true

            if (userInfo.sportsList.size > 2) {
                binding.sports3.text = userInfo.sportsList[2]
                binding.sports3.isVisible = true
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
