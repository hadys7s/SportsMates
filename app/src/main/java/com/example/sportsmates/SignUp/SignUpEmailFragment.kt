package com.example.sportsmates.SignUp

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
import com.google.android.material.textfield.TextInputLayout

class SignUpEmailFragment : Fragment() {
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
            Toast.makeText(activity,"Passwords don't match",Toast.LENGTH_SHORT).show()
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
                replaceFragment(SignUpUserInfoFragment.newInstance())

            } else {
                return@setOnClickListener
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransiction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransiction.replace(R.id.container, fragment)
            .addToBackStack(SignUpEmailFragment::class.java.simpleName).commit()


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

}