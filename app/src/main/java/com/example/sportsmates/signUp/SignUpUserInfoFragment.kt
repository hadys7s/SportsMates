package com.example.sportsmates.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignUpUserInfoFragmentBinding
import com.example.sportsmates.signUp.data.model.User
import com.google.android.material.textfield.TextInputLayout

class SignUpUserInfoFragment : Fragment() {
    private var _binding: SignUpUserInfoFragmentBinding? = null
    private val binding get() = _binding!!

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
        setStepper()
        setUpCityMenu()
        setUpGenderMenu()
        nextButton()

    }

    private fun setUpCityMenu() {
        val items = listOf("Damietta", "Mansoura")
        val adapter = ArrayAdapter(requireContext(), R.layout.sign_up_spinner_list_item, items)
        (binding.chooseCity.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpGenderMenu() {
        val items = listOf("Male", "Female")
        val adapter = ArrayAdapter(requireContext(), R.layout.sign_up_spinner_list_item, items)
        (binding.chooseGender.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun nextButton() {
        binding.nextButton2.setOnClickListener {
            if (validation())
                replaceFragment(SignUpSportsFragment.newInstance(forwardUserInfo()))
        }
    }

    private fun validation(): Boolean {
        return (validateUserInfoFieldIsEmpty(binding.chooseCity) && validateUserInfoFieldIsEmpty(
            binding.edAge
        ) && validateUserInfoFieldIsEmpty(
            binding.chooseGender
        )

                )

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransiction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransiction.replace(R.id.container, fragment)
            .addToBackStack(SignUpUserInfoFragment::class.java.simpleName).commit()


    }

    private fun forwardUserInfo(): User? {
        var user: User? = arguments?.getParcelable(USER_DATA)

        user?.city = binding.chooseCity.editText?.text.toString()
        user?.age = binding.edAge.editText?.text.toString()
        user?.gender = binding.chooseGender.editText?.text.toString()
        return user
    }


    private fun setStepper() {

        val stepsList = ArrayList<StepBean>()
        val stepBean0 = StepBean("1", 1)
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