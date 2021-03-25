package com.example.sportsmates.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baoyachi.stepview.bean.StepBean
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignUpSportFargmentBinding
import com.example.sportsmates.ext.openTopActivity
import com.example.sportsmates.ext.replaceFragment
import com.example.sportsmates.signUp.SignUpActivity
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.viewmodel.SignUpViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import com.google.android.material.chip.Chip

class SignUpSportsFragment : Fragment() {
    private var _binding: SignUpSportFargmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpSportFargmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun doneButton() {
        binding.doneButton.setOnClickListener {
            if (validateSelectOnlyThreeSports()) {
                // view model signUp
                viewModel.onRegisterButtonCLicked(signUpUserInfo())

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStepper()
        doneButton()
        attachEventObservers()

    }

    fun attachEventObservers() {
        viewModel.signUpSuccess.observe(this, Observer {
            // redirect login
            openTopActivity(activity, SignUpActivity())

        })

        viewModel.signUpFailed.observe(this, Observer { errorMessage ->
            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()

        })

    }

    private fun validateSelectOnlyThreeSports(): Boolean {
        return when {
            getSelectedSports().size > 3 -> {
                Toast.makeText(activity, "Please Select Only 3 Sports ", Toast.LENGTH_SHORT).show()
                false

            }
            getSelectedSports().isEmpty() -> {
                Toast.makeText(
                    activity,
                    "Please Select Your favourites Sports ",
                    Toast.LENGTH_SHORT
                )
                    .show()
                false
            }
            else -> true
        }

    }

    private fun getSelectedSports(): List<String> {
        return binding.sportsGroup.children
            .filter { ((it as Chip).isChecked) }
            .map { (it as Chip).text.toString() }
            .toList()
    }

    private fun signUpUserInfo(): User? {
        var user: User? = arguments?.getParcelable(USER_DATA)
        user?.sportsList = getSelectedSports()
        return user
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val USER_DATA = "userData"

        fun newInstance(user: User?) =
            SignUpSportsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_DATA, user)
                }
            }
    }
}

