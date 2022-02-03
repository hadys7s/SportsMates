package com.example.sportsmates.signUp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignUpUserInfoFragmentBinding
import com.example.sportsmates.ext.pushFragment
import com.example.sportsmates.ext.setStepper
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.viewmodel.SignUpViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpUserInfoFragment : Fragment() {
    private var _binding: SignUpUserInfoFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpUserInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStepper(1, -1, -1, binding.stepView)
        setupMenu("Damietta", "Mansoura", binding.chooseCity.editText)
        setupMenu("Male", "Female", binding.chooseGender.editText)
        nextButton()

    }

    private fun setupMenu(option1: String, option2: String, editText: EditText?) {
        val items = listOf(option1, option2)
        val adapter = ArrayAdapter(requireContext(), R.layout.sign_up_spinner_list_item, items)
        (editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun nextButton() {
        binding.nextButton2.setOnClickListener {
            if (validation())
                pushFragment(
                    SignUpSportsFragment.newInstance(forwardUserInfo()),
                    containerViewId = R.id.container
                )

        }
    }
    private fun forwardUserInfo(): User? {
        val user: User? = arguments?.getParcelable(USER_DATA)

        user?.city = binding.chooseCity.editText?.text.toString()
        user?.age = binding.edAge.editText?.text.toString()
        user?.gender = binding.chooseGender.editText?.text.toString()
        return user
    }

    private fun validation(): Boolean {
        return (validateUserInfoFieldIsEmpty(binding.chooseCity) && validateUserInfoFieldIsEmpty(
            binding.edAge
        ) && validateUserInfoFieldIsEmpty(binding.chooseGender))
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

    companion object {
        private const val USER_DATA = "userData"

        fun newInstance(user: User) =
            SignUpUserInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_DATA, user)

                }
            }

    }
}