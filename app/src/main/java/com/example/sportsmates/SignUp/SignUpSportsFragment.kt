package com.example.sportsmates.SignUp

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignUpSportFargmentBinding
import com.example.sportsmates.databinding.SignUpSportsFargmentBinding

class SignUpSportsFragment :Fragment(R.layout.sign_up_sport_fargment) {
    private lateinit var binding: SignUpSportFargmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= SignUpSportFargmentBinding.bind(view)
        setStepper()
        doneButton()

    }
    private fun doneButton(){
        binding.doneButton.setOnClickListener {

        }
    }


    private fun setStepper() {

        val stepsList = ArrayList<StepBean>()
        val stepBean0 = StepBean("1", 1)
        val stepBean1 = StepBean("2", 1)
        val stepBean2 = StepBean("3", -1)
        stepsList.add(stepBean0)
        stepsList.add(stepBean1)
        stepsList.add(stepBean2)
                binding.stepper.setStepViewTexts(stepsList)
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
            SignUpSportsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}