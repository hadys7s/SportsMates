package com.example.sportsmates.booking.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.booking.data.BookingModel
import com.example.sportsmates.booking.domain.MailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingViewModel(
    private val userRepository: UserRepository,
    private val mailService: MailService
) : ViewModel() {

    val sendingMessageFauiler = MutableLiveData<String>()
    val sendingMessageSuccess = MutableLiveData<String>()

    fun sendEmailToUser(
        bookingModel: BookingModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mailService.sendmail(
                BookingModel(
                    userRepository.getCashedUser()?.email,
                    bookingModel.mailSubject,
                    bookingModel.mailBody,
                ), onSuccessListener = {
                    sendingMessageSuccess.value =
                        "Booking is Done Successfully You will Receive A Message Shortly "

                }, onFailureListener = {
                    sendingMessageFauiler.value = "Something happened Wrong Try Again"
                }

            )
        }
    }

    fun sendEmailToServiceProvider(
        bookingModel: BookingModel
    ) {
        mailService.sendmail(
            BookingModel(
                userRepository.getCashedUser()?.email,
                mailSubject = bookingModel.mailSubject,
                mailBody = bookingModel.mailBody
            ), onSuccessListener = {
                sendingMessageSuccess.value =
                    "Booking is Done Successfully You will Receive A Message Shortly "
            }, onFailureListener = {
                sendingMessageFauiler.value = "Something happened Wrong Try Again"
            }
        )
    }

    fun getCashedUser() = userRepository.getCashedUser()
}