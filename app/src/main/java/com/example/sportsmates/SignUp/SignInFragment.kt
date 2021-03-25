package com.example.sportsmates.SignUp
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.UserSignInFragmentBinding
import com.example.sportsmates.login.SignInViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(R.layout.user_sign_in_fragment) {
    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: UserSignInFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= UserSignInFragmentBinding.bind(view)
        signUp()
    }



    private fun attachEventObservers() {
        viewModel.loginSuccess.observe(this, Observer { user ->
            //  redirect home

        })
        viewModel.loginFailed.observe(this, Observer { errorMessage ->
        })
    }
    private fun signUp (){
        binding.tvSelectableSignup.setOnClickListener {
              replaceFragment(SignUpEmailFragment.newInstance())
        }
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransiction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransiction.replace(R.id.container, fragment)
            .addToBackStack(SignUpUserInfoFragment::class.java.simpleName).commit()


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