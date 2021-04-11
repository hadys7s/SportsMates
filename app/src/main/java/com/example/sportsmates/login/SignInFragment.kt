package com.example.sportsmates.SignUp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.SignInFragmentBinding
import com.example.sportsmates.ext.openTopActivity
import com.example.sportsmates.ext.pushFragment
import com.example.sportsmates.home.MainActivity
import com.example.sportsmates.login.SignInViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel
import www.sanju.motiontoast.MotionToast

class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModel()
    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: AlertDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachCLickListeners()
        attachEventObservers()
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
            dismissAlertDialog()
            openTopActivity(activity, MainActivity())
        })
        viewModel.loginFailed.observe(this, Observer { errorMessage ->
            dismissAlertDialog()
            MotionToast.darkToast(
                activity!!, "Faild", errorMessage,
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(activity!!, R.font.helvetica_regular)
            )

        })
    }

    private fun attachCLickListeners() {
        binding.tvSelectableSignup.setOnClickListener {
            pushFragment(SignUpEmailFragment.newInstance(), containerViewId = R.id.container)
        }

        binding.loginButton.setOnClickListener {
            if (validation()) {
                startAlertDialog()
                login()
            }

        }
    }

    private fun login() {
        viewModel.login(
            binding.edEmail.editText?.text.toString(),
            binding.edPassword.editText?.text.toString().trim()
        )
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

    private fun startAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.progress_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun dismissAlertDialog() {
        dialog.dismiss()
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