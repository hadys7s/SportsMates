package com.example.sportsmates.SignUp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignUpUserInfoFragmentBinding

class SignUpUserInfoFragment : Fragment(R.layout.sign_up_user_info_fragment) {
    private lateinit var binding: SignUpUserInfoFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= SignUpUserInfoFragmentBinding.bind(view)
        setStepper()
        setUpCityMenu()
        setUpGenderMenu()
        nextButton()

    }

    private fun setUpCityMenu(){
        val items = listOf("Damietta","Mansoura")
        val adapter = ArrayAdapter(requireContext(), R.layout.sign_up_spinner_list_item, items)
        (binding.chooseCity.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    private fun setUpGenderMenu(){
        val items = listOf("Male","Female")
        val adapter = ArrayAdapter(requireContext(), R.layout.sign_up_spinner_list_item, items)
        (binding.chooseGender.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    private fun nextButton(){
        binding.nextButton2.setOnClickListener {
            replaceFragment(SignUpSportsFragment.newInstance())
        }
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransiction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransiction.replace(R.id.container, fragment)
            .addToBackStack(SignUpUserInfoFragment::class.java.simpleName).commit()


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
    companion object {

        @JvmStatic
        fun newInstance() =
            SignUpUserInfoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}