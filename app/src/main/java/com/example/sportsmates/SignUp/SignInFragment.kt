package com.example.sportsmates.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignInFragmentBinding
import com.example.sportsmates.login.SignInViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModel()
    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachCLickListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun attachEventObservers() {
        viewModel.loginSuccess.observe(this, Observer { user ->
            //  redirect home

        })
        viewModel.loginFailed.observe(this, Observer { errorMessage ->
        })
    }

    private fun attachCLickListeners() {
        binding.tvSelectableSignup.setOnClickListener {
            replaceFragment(SignUpEmailFragment.newInstance())
        }

        binding.loginButton.setOnClickListener {
            if (validation()) {

            }

        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransiction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransiction.replace(R.id.container, fragment)
            .addToBackStack(SignUpUserInfoFragment::class.java.simpleName).commit()


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