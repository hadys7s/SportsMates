package com.example.sportsmates.auth.presentation.signUp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sportsmates.R
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.presentation.signUp.viewmodel.SignUpSteps
import com.example.sportsmates.auth.presentation.signUp.viewmodel.SignUpViewModel
import com.example.sportsmates.databinding.SignUpSportFargmentBinding
import com.example.sportsmates.ext.*
import com.example.sportsmates.home.presentation.activity.MainActivity
import com.example.sportsmates.networking.Resource
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.sharedViewModel


class SignUpSportsFragment : Fragment() {
    private var _binding: SignUpSportFargmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignUpSportFargmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun doneButton() {
        with(binding.doneButton){
            btnText.text = getString(R.string.done_button)
            myBtn.setOnClickListener {
                if (validateSelectOnlyThreeSports()) {
                    // view model signUp
                    viewModel.initInput(signUpUserInfo(), SignUpSteps.STEP_THREE)
                    viewModel.signUp()

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStepper(1, 1, -1, binding.stepper)
        doneButton()
        attachEventObservers()

    }

    private fun attachEventObservers() {
        // redirect login
        lifecycleScope.launchWhenStarted {
            viewModel.signUpState.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.doneButton.myBtn.showLoading()
                    }
                    is Resource.Error -> {
                        binding.doneButton.myBtn.hideLoading()
                        displayErrorToast("Error ", it.exception.message!!)
                    }
                    is Resource.Success -> {
                        binding.doneButton.myBtn.hideLoading()
                        openTopActivity(activity, MainActivity())
                    }
                }
            }
        }
    }

    private fun validateSelectOnlyThreeSports(): Boolean {
        return when {
            getSelectedSports().size > 3 -> {
                displayWarningToast("Warning", "Please Select Only 3 Sports")
                false

            }
            getSelectedSports().isEmpty() -> {
                displayWarningToast("Warning", "Please Select Your favourites Sports")
                false
            }
            else -> true
        }

    }

    private fun getSelectedSports(): MutableList<String> {
        return binding.sportsGroup.children
            .filter { ((it as Chip).isChecked) }
            .map { (it as Chip).text.toString() }
            .toMutableList()
    }

    private fun signUpUserInfo(): User{
        val user = User()
        user.sportsList = getSelectedSports()
      //  user.id = Firebase.auth.currentUser.uid
        return user
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val USER_DATA = "userData"

        fun newInstance() =
            SignUpSportsFragment()

    }
}

