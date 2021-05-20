package com.example.sportsmates.editProfile

import com.example.sportsmates.editProfile.EditProfileActivity
import com.example.sportsmates.editProfile.EditProfileViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityUpdateUserInfoBinding
import com.example.sportsmates.ext.*
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.utils.Constants.EDIT
import com.example.sportsmates.utils.Constants.HINT
import com.example.sportsmates.utils.Constants.NAME_CITY
import com.example.sportsmates.utils.Constants.NAME_SPORT
import com.example.sportsmates.utils.Constants.USER
import com.example.sportsmates.utils.InfoType
import com.example.sportsmates.utils.InfoType.*
import com.google.android.material.chip.Chip
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class UpdateUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserInfoBinding
    private val viewModel: EditProfileViewModel by viewModel()
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListener()
        attachObservers()
        setupMenu(getString(R.string.damietta), getString(R.string.mansoura), binding.chooseCity.editText)
        getEditField()
    }

    private fun getEditField() {
        val intent = intent
        when {
            intent.hasExtra(NAME_SPORT) -> {
                user = intent.getParcelableExtra(USER)
                binding.editBox.visibility = View.GONE
                binding.sportsGroup.visibility = VISIBLE
            }
            intent.hasExtra(NAME_CITY) -> {
                user = intent.getParcelableExtra(USER)
                binding.editBox.visibility = View.GONE
                binding.chooseCity.visibility = VISIBLE
            }
            else -> {
                val text = intent.getStringExtra(EDIT)
                val hint = intent.getStringExtra(HINT)
                user = intent.getParcelableExtra(USER)
                binding.editField.setText(text)
                binding.editBox.hint = hint
            }
        }
    }

    private fun getUpdatedUserInfo() {
        when {
            binding.editBox.visibility == VISIBLE -> {
                val text = binding.editBox.editText?.text.toString()
                when (binding.editBox.hint.toString()) {
                    getString(R.string.name) -> {
                        updateUserInfo(text, NAME)
                    }
                    getString(R.string.mail) -> {
                        updateUserAuthentication(
                            text,
                            user!!.email!!,
                            text,
                            user!!.password!!,
                            MAIL
                        )
                    }
                    getString(R.string.password) -> {
                        updateUserAuthentication(
                            text,
                            user!!.email!!,
                            text,
                            user!!.password!!,
                            PASSWORD
                        )
                    }
                    getString(R.string.bio) -> {
                        updateUserInfo(text,BIO)
                    }
                }
            }
            binding.sportsGroup.visibility == VISIBLE -> {
                if (validateSelectOnlyThreeSports()) {
                    getSelectedSports()?.let { udpateUserSportsList(it) }
                }
            }
            else -> {
                val text = binding.chooseCity.editText?.text.toString()
                updateUserInfo(text,CITY)
            }
        }
    }

    private fun setupMenu(option1: String, option2: String, editText: EditText?) {
        val items = listOf(option1, option2)
        val adapter = ArrayAdapter(this, R.layout.sign_up_spinner_list_item, items)
        (editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun attachObservers() {
        viewModel.updateInfoSuccess.observe(this, Observer {
            displaySuccessToast(getString(R.string.success_toast_title), it!!)
            onBackPressed()
        })
        viewModel.updateInfoFailuer.observe(this, Observer {
            displayErrorToast(getString(R.string.error_toast_title), it!!)
        })
    }

    private fun attachClickListener() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.updateBtn.setOnClickListener {
            getUpdatedUserInfo()
        }
    }

    private fun updateUserInfo(
        adjustedData: String,
        infoType: InfoType
    ) {
        when (infoType) {
            NAME -> {
                viewModel.updateUserName(adjustedData)
            }
           CITY -> {
                viewModel.updateUserCity(adjustedData)
            }
            else -> {
                viewModel.updateUserBio(adjustedData)
            }
        }

    }

    private fun updateUserAuthentication(
        newEmail: String,
        oldEmail: String,
        newPassword: String,
        oldPassword: String,
        infoType: InfoType
    ) {
        if (infoType == MAIL) {
            viewModel.updateUserEmail(newEmail, oldEmail, oldPassword)
        } else {
            viewModel.updateUserPassword(oldEmail, newPassword, oldPassword)
        }
    }

    private fun udpateUserSportsList(newSportsList: List<String>) {
        viewModel.updateUserSportsList(newSportsList)
    }

    private fun validateSelectOnlyThreeSports(): Boolean {
        return when {
            getSelectedSports()?.size!! > 3 -> {
                displayWarningToast(getString(R.string.warning_toast_title), "Please Select Only 3 Sports")
                false

            }
            getSelectedSports()!!.isEmpty() -> {
                displayWarningToast(getString(R.string.warning_toast_title), "Please Select Your favourites Sports")
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

}