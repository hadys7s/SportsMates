package com.example.sportsmates.booking

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.coach.CoachUiModel
import com.example.sportsmates.databinding.ActivityBookingBinding
import com.example.sportsmates.ext.changeStatusBarColor
import com.example.sportsmates.ext.displayErrorToast
import com.example.sportsmates.ext.displaySuccessToast
import com.example.sportsmates.ext.displayWarningToast
import com.example.sportsmates.home.events.Event
import com.example.sportsmates.place.PlaceUiModel
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private val viewModel: BookingViewModel by viewModel()
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        attachOnCLickListener()
        checkIfUserBookAnEvent()
        attachObservers()
    }

    private fun validateUserInfoFiledisEmpty(textInputLayout: TextInputLayout): Boolean {
        val name = textInputLayout.editText?.text.toString()
        return if (name.isEmpty()) {
            textInputLayout.error = "Field Cannot be empty"
            false
        } else {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
            true
        }
    }
    private fun checkIfUserBookAnEvent(){
        if (intent.hasExtra(EVENT_ITEM)){
            binding.timeTv.visibility=View.GONE
            binding.groubContainer.visibility=View.GONE
            binding.edDate2.visibility=View.GONE
            binding.dateTv.visibility=View.GONE
        }
    }

    private fun validateMasterCardNumberIsCorrect(): Boolean {
        val masterCardNumber = binding.edMasterCard.editText?.text.toString().length
        return masterCardNumber == 16
    }

    private fun validateCvvIsCorecct(): Boolean {
        val cvvNumber = binding.cvv.editText?.text.toString().length
        return cvvNumber == 3
    }

    private fun validateAllFields(): Boolean {
        return validateUserInfoFiledisEmpty(binding.edDate) &&validateUserInfoFiledisEmpty(binding.edMasterCard) && validateUserInfoFiledisEmpty(
            binding.cardExpierd
        ) && validateUserInfoFiledisEmpty(
            binding.cvv
        )&& validateMasterCardNumberIsCorrect() &&
                validateCvvIsCorecct()
    }

    private fun validateSelectOnlyOneTime(): Boolean {
        return when {
            getSelectedTime()?.size!! > 1 -> {
                displayWarningToast("Warning", "Please Select Only 1 Time")
                false

            }
            getSelectedTime()!!.isEmpty() -> {
                displayWarningToast("Warning", "Please Select Time ")
                false
            }
            else -> true
        }

    }

    private fun getSelectedTime(): MutableList<String>? {
        return binding.timeGroup.children
            .filter { ((it as Chip).isChecked) }
            .map { (it as Chip).text.toString() }
            .toMutableList()
    }

    private fun setDateDialog() {
        val calendar = Calendar.getInstance()
        val listner = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val _month = month + 1
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, _month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.edDate2.setText("$dayOfMonth/ $_month / $year ")
        }
        DatePickerDialog(
            this,
            listner,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    private fun showLoading() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        builder.setView(inflater.inflate(R.layout.progress_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
    private fun hideLoading() {
        dialog.dismiss()
    }

    private fun sendEmailWithArguments() {
        val couch: CoachUiModel? = intent.getParcelableExtra((COUCH_ITEM))
        val place: PlaceUiModel? = intent.getParcelableExtra((PLACE_ITEM))
        val time = getSelectedTime()?.joinToString("")
        when {
            place != null -> {
                viewModel.sendEmailToUser(place.name!!, time!!,binding.edDate2.text.toString())
                viewModel.sendEmailToPlace(place.email!!, time,binding.edDate2.text.toString())
            }
            couch != null -> {
                viewModel.sendEmailToUser(couch.name!!, time!!,binding.edDate2.text.toString())
                viewModel.sendEmailToCouch(couch.email!!, time,binding.edDate2.text.toString())
            }
            else -> {

            }
        }

    }

    fun attachOnCLickListener() {
        binding.confirmBtn.setOnClickListener {
            if (validateAllFields()) {
                if (validateSelectOnlyOneTime()) {
                    sendEmailWithArguments()
                    showLoading()
                }
            } else {
                displayErrorToast("Error", "Your Master Card Number Or Cvv is Wrong Ty Again !")
            }
        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.edDate2.setOnClickListener {
            setDateDialog()
        }
    }

    fun attachObservers() {
        viewModel.sendingMessageSuccess.observe(this, Observer {
            displaySuccessToast("Success", it)
            hideLoading()
            onBackPressed()
        })
        viewModel.sendingMessageFauiler.observe(this, Observer {
            displayErrorToast("Error", it)
        })
    }

    companion object {
        private const val PLACE_ITEM = "placeItem"
        private const val COUCH_ITEM = "couchItem"
        private const val EVENT_ITEM = "eventItem"
        fun start(
            activity: Activity?,
            placeItem: PlaceUiModel? = null,
            couchItem: CoachUiModel? = null,
            eventItem: Event? = null
        ) {
            val intent = Intent(activity, BookingActivity::class.java)
            intent.putExtra(PLACE_ITEM, placeItem)
            intent.putExtra(COUCH_ITEM, couchItem)
            intent.putExtra(EVENT_ITEM, eventItem)
            activity?.startActivity(intent)
        }
    }
}