package com.example.sportsmates.auth.presentation.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportsmates.R
import com.example.sportsmates.auth.presentation.signUp.fragments.SignUpEmailFragment
import com.example.sportsmates.databinding.SignInFragmentBinding
import com.example.sportsmates.ext.*
import com.example.sportsmates.home.presentation.activity.MainActivity
import com.example.sportsmates.networking.Resource
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModel()
    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachCLickListeners()
        listenToLoginState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun listenToLoginState() {
        stateCollector(viewModel.loginState){
            when (it) {
                is Resource.Loading -> {
                    binding.loginButton.myBtn.showLoading()
                }
                is Resource.Error -> {
                    binding.loginButton.myBtn.hideLoading()
                    displayErrorToast("Failed", it.exception.message!!)
                }
                is Resource.Success -> {
                    binding.loginButton.myBtn.hideLoading()
                    openTopActivity(activity, MainActivity())
                }
            }
        }
    }

    private fun attachCLickListeners() {
        binding.tvSelectableSignup.setOnClickListener {
            pushFragment(SignUpEmailFragment.newInstance(), containerViewId = R.id.container)
        }
        with(binding.loginButton) {
            btnText.text = getString(R.string.login)
            myBtn.setOnClickListener {
                if (validation())
                    login()
            }
        }
    }

    private fun login() {
        viewModel.login(
            binding.edEmail.editText?.text.toString(),
            binding.edPassword.editText?.text.toString().trim()
        )
    }

    private fun validation(): Boolean {
        return (validateUserInfoFieldIsEmpty(binding.edEmail) && validateUserInfoFieldIsEmpty(
            binding.edPassword
        ))

    }

    private fun validateUserInfoFieldIsEmpty(textInputLayout: TextInputLayout): Boolean {
        val text = textInputLayout.editText?.text.toString()
        return if (text.isEmpty()) {
            textInputLayout.error = "Field Cannot be empty"
            false
        } else {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
            true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SignInFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}