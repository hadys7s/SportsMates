package com.example.sportsmates.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sportsmates.databinding.SignUpSportFargmentBinding
import com.example.sportsmates.ext.*
import com.example.sportsmates.home.MainActivity
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.signUp.viewmodel.SignUpViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import com.google.android.material.chip.Chip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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
                viewModel.onDoneButtonClicked(signUpUserInfo())

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStepper(1, 1, -1, binding.stepper)
        doneButton()
        attachEventObservers()

    }

    fun attachEventObservers() {
        viewModel.signUpSuccess.observe(this, Observer {
            // redirect login
            openTopActivity(activity, MainActivity())

        })

        viewModel.signUpFailed.observe(this, Observer { errorMessage ->
            displayErrorToast("Error ",errorMessage)

        })

    }

    private fun validateSelectOnlyThreeSports(): Boolean {
        return when {
            getSelectedSports()?.size!! > 3 -> {
                displayWarningToast("Warning","Please Select Only 3 Sports")
                false

            }
            getSelectedSports()!!.isEmpty() -> {
                displayWarningToast("Warning","Please Select Your favourites Sports")
                false
            }
            else -> true
        }

    }

    private fun getSelectedSports(): MutableList<String>? {
        return binding.sportsGroup.children
            .filter { ((it as Chip).isChecked) }
            .map { (it as Chip).text.toString() }
            .toMutableList()
    }

    private fun signUpUserInfo(): User? {
        var user: User? = arguments?.getParcelable(USER_DATA)
        user?.sportsList = getSelectedSports()
        user?.id=Firebase.auth.currentUser.uid
        return user
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

