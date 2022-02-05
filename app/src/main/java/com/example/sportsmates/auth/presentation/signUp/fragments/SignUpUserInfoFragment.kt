package com.example.sportsmates.auth.presentation.signUp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.sportsmates.R
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.presentation.signUp.viewmodel.SignUpSteps
import com.example.sportsmates.auth.presentation.signUp.viewmodel.SignUpViewModel
import com.example.sportsmates.databinding.SignUpUserInfoFragmentBinding
import com.example.sportsmates.ext.pushFragment
import com.example.sportsmates.ext.setStepper
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SignUpUserInfoFragment : Fragment() {
    private var _binding: SignUpUserInfoFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by sharedViewModel()

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
        setUserInfoFromInput()
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
                viewModel.initInput(forwardUserInfo(), SignUpSteps.STEP_TWO)
            pushFragment(
                SignUpSportsFragment.newInstance(),
                containerViewId = R.id.container
            )

        }
    }

    private fun setUserInfoFromInput() {
        val info = viewModel.getInfo()
        binding.chooseCity.editText?.setText(info.city)
        binding.edAge.editText?.setText(info.age)
        binding.chooseGender.editText?.setText(info.gender)
    }

    private fun forwardUserInfo(): User {
        val user = User()
        user.city = binding.chooseCity.editText?.text.toString()
        user.age = binding.edAge.editText?.text.toString()
        user.gender = binding.chooseGender.editText?.text.toString()
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

        fun newInstance() =
            SignUpUserInfoFragment()

    }
}