package com.example.sportsmates.editProfile

import android.app.Activity
import android.content.Intent
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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.coach.CoachDetailsActivity
import com.example.sportsmates.coach.CoachUiModel
import com.example.sportsmates.databinding.ActivityUpdateUserInfoBinding
import com.example.sportsmates.ext.*
import com.example.sportsmates.utils.Constants._BIO
import com.example.sportsmates.utils.Constants._MAIL
import com.example.sportsmates.utils.Constants._NAME
import com.example.sportsmates.utils.Constants._PASSWORD
import com.example.sportsmates.utils.InfoType
import com.example.sportsmates.utils.InfoType.*
import com.google.android.material.chip.Chip
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class UpdateUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserInfoBinding
    private val viewModel: EditProfileViewModel by viewModel()
    var oldPassword: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachClickListener()
        attachObservers()
        setupMenu(
            getString(R.string.damietta),
            getString(R.string.mansoura),
            binding.chooseCity.editText
        )
        getEditField()
    }

    private fun getEditField() {
        val intent = intent
        val infoType = intent.getStringExtra(EDITABLE_USER_INFO_TYPE)
        oldPassword = intent.getStringExtra(EDITABLE_USER_OLD_PASSWORD_ITEM)
        when (infoType) {
            SPORTS.toString() -> {
                binding.editBox.visibility = View.GONE
                binding.sportsGroup.visibility = VISIBLE
            }
            CITY.toString() -> {
                binding.editBox.visibility = View.GONE
                binding.chooseCity.visibility = VISIBLE
            }
            else -> {
                val text = intent.getStringExtra(EDITABLE_USER_OLD_VALUE)
                val hint = intent.getStringExtra(EDITABLE_USER_HINT_ITEM)
                binding.editField.setText(text)
                binding.editBox.hint = hint
            }
        }
    }

    private fun getUpdatedUserInfo() {
        when {
            binding.editBox.visibility == VISIBLE -> {
                val adjustedData = binding.editBox.editText?.text.toString()
                when (binding.editBox.hint.toString()) {
                    _NAME -> {
                        updateUserInfo(adjustedData, NAME)
                    }
                    _MAIL -> {
                        updateUserAuthentication(
                            newEmail = adjustedData,
                            oldPassword = oldPassword!!,
                            infoType = MAIL

                        )
                    }
                    _PASSWORD -> {
                        // TODO: 5/21/2021  ("figure way to get password")
                        updateUserAuthentication(
                            newPassword = adjustedData,
                            oldPassword = oldPassword!!,
                            infoType = PASSWORD

                        )
                    }
                    _BIO -> {
                        updateUserInfo(adjustedData, BIO)
                    }
                }
            }
            binding.sportsGroup.visibility == VISIBLE -> {
                if (validateSelectOnlyThreeSports()) {
                    getSelectedSports()?.let { udpateUserSportsList(it) }
                }
            }
            else -> {
                val adjustedData = binding.chooseCity.editText?.text.toString()
                updateUserInfo(adjustedData, CITY)
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
        newEmail: String = "",
        newPassword: String = "",
        oldPassword: String,
        infoType: InfoType
    ) {
        if (infoType == MAIL) {
            viewModel.updateUserEmail(newEmail, oldPassword)
        } else {
            viewModel.updateUserPassword(newPassword, oldPassword)
        }
    }

    private fun udpateUserSportsList(newSportsList: List<String>) {
        viewModel.updateUserSportsList(newSportsList)
    }

    private fun validateSelectOnlyThreeSports(): Boolean {
        return when {
            getSelectedSports()?.size!! > 3 -> {
                displayWarningToast(
                    getString(R.string.warning_toast_title),
                    "Please Select Only 3 Sports"
                )
                false

            }
            getSelectedSports()!!.isEmpty() -> {
                displayWarningToast(
                    getString(R.string.warning_toast_title),
                    "Please Select Your favourites Sports"
                )
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

    companion object {
        // TODO: 5/21/2021 refactor to class
        private const val EDITABLE_USER_OLD_VALUE = "userOldValue"
        private const val EDITABLE_USER_HINT_ITEM = "userInfoItem"
        private const val EDITABLE_USER_OLD_PASSWORD_ITEM = "userOldPasswordItem"
        private const val EDITABLE_USER_INFO_TYPE = "userInfoType"
        fun start(
            activity: Activity?,
            oldValue: String="",
            oldPassword: String = "",
            hint: String = "",
            infoType: String = ""
        ) {
            val intent = Intent(activity, UpdateUserInfoActivity::class.java)
            intent.putExtra(EDITABLE_USER_OLD_VALUE, oldValue)
            intent.putExtra(EDITABLE_USER_HINT_ITEM, hint)
            intent.putExtra(EDITABLE_USER_OLD_PASSWORD_ITEM, oldPassword)
            intent.putExtra(EDITABLE_USER_INFO_TYPE, infoType)

            activity?.startActivity(intent)
        }
    }

}