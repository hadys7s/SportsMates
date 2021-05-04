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
import com.example.sportsmates.utils.InfoType
import com.example.sportsmates.utils.InfoType.*
import com.google.android.material.chip.Chip
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
        setupMenu("Damietta", "Mansoura", binding.chooseCity.editText)
        getEditField()
    }

    private fun getEditField() {
        val intent = intent
        when {
            intent.hasExtra("Sports") -> {
                user = intent.getParcelableExtra("user")
                binding.editBox.visibility = View.GONE
                binding.sportsGroup.visibility = VISIBLE
            }
            intent.hasExtra("City") -> {
                user = intent.getParcelableExtra("user")
                binding.editBox.visibility = View.GONE
                binding.chooseCity.visibility = VISIBLE
            }
            else -> {
                val text = intent.getStringExtra("Edit")
                val hint = intent.getStringExtra("Hint")
                user = intent.getParcelableExtra("user")
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
                    "Name" -> {
                        updateUserInfo(text, NAME)
                    }
                    "Mail" -> {
                        updateUserInfo(text, MAIL)
                        viewModel.updateUserAuthentication(
                            text,
                            user!!.email!!,
                            user!!.password!!,
                            user!!.password!!
                        )
                    }
                    "Password" -> {
                        updateUserInfo(text, PASSWORD)
                        viewModel.updateUserAuthentication(
                            user!!.email!!,
                            user!!.email!!,
                            text,
                            user!!.password!!
                        )
                    }
                    "Bio" -> {
                        updateUserInfo(text, BIO)
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
                updateUserInfo(text, CITY)
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
            displaySuccessToast("Success", it!!)
        })
        viewModel.updateInfoFailuer.observe(this, Observer {
            displayErrorToast("Error", it!!)
        })
    }

    private fun attachClickListener() {
        binding.backBtn.setOnClickListener {
            openTopActivity(this, EditProfileActivity())
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
                viewModel.updateUserInfo(
                    User(
                        name = adjustedData,
                        email = user!!.email,
                        password = user!!.password,
                        city = user!!.city,
                        sportsList = user!!.sportsList
                    )
                )
            }
            MAIL -> {
                viewModel.updateUserInfo(
                    User(
                        name = user!!.name,
                        email = adjustedData,
                        password = user!!.password,
                        city = user!!.city,
                        sportsList = user!!.sportsList

                    )
                )
            }
            CITY -> {
                viewModel.updateUserInfo(
                    User(
                        name = user!!.name,
                        email = user!!.email,
                        password = user!!.password,
                        city = adjustedData,
                        sportsList = user!!.sportsList
                    )
                )
            }
            PASSWORD -> {
                viewModel.updateUserInfo(
                    User(
                        name = user!!.name,
                        email = user!!.email,
                        password = adjustedData,
                        city = user!!.city,
                        sportsList = user!!.sportsList
                    )
                )
            }
            else -> {
            }
        }

    }

    private fun udpateUserSportsList(newSportsList: List<String>) {
        viewModel.updateUserInfo(
            User(
                name = user!!.name,
                email = user!!.email,
                password = user!!.password,
                city = user!!.city,
                sportsList = newSportsList
            )
        )
    }

    private fun validateSelectOnlyThreeSports(): Boolean {
        return when {
            getSelectedSports()?.size!! > 3 -> {
                displayWarningToast("Warning", "Please Select Only 3 Sports")
                false

            }
            getSelectedSports()!!.isEmpty() -> {
                displayWarningToast("Warning", "Please Select Your favourites Sports")
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