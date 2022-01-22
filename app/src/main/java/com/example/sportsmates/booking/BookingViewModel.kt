package com.example.sportsmates.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.UserPreferences
import com.example.sportsmates.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class BookingViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    val sendingMessageFauiler = MutableLiveData<String>()
    val sendingMessageSuccess = MutableLiveData<String>()

    fun sendEmailToUser(
        name: String,
        time: String,
        date: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val prop = Properties()
            prop["mail.smtp.host"] = "smtp.gmail.com"
            prop["mail.smtp.socketFactory.port"] = "465"
            prop["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            prop["mail.smtp.auth"] = "true"
            prop["mail.smtp.port"] = "465"
            val session =
                javax.mail.Session.getDefaultInstance(prop, object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Constants.APP_EMAIL, Constants.APP_PASSWORD)
                    }
                })
            try {
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(Constants.APP_EMAIL))
                message.setRecipients(
                    javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(userPreferences.email)
                )
                message.subject = "Booking completed successfully "
                message.setText(
                    "hello," + System.lineSeparator() +
                            "Booking completed successfully, you successfuly book with   $name   in  $date  at $time " + System.lineSeparator() + "$name Will contact you soon"
                )
                Transport.send(message)
                sendingMessageSuccess.postValue("Booking is Done Successfully You will Receive A Message Shortly ")
            } catch (e: MessagingException) {
                sendingMessageFauiler.postValue("Something happened Wrong Try Again")
            }
        }
    }

    fun sendEmailToCouch(
        reciverEmail: String,
        time: String,
        date: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val prop = Properties()
            prop["mail.smtp.host"] = "smtp.gmail.com"
            prop["mail.smtp.socketFactory.port"] = "465"
            prop["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            prop["mail.smtp.auth"] = "true"
            prop["mail.smtp.port"] = "465"
            val session =
                javax.mail.Session.getDefaultInstance(prop, object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Constants.APP_EMAIL, Constants.APP_PASSWORD)
                    }
                })
            try {
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(Constants.APP_EMAIL))
                message.setRecipients(
                    javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(reciverEmail)
                )

                message.subject = "You Have A Client ..  "
                message.setText(
                    "hello," + System.lineSeparator() + "  ${userPreferences.name}  is Booking With You In  $date  In Time $time" + System.lineSeparator() + "Contact With Him For More Details " + userPreferences.email
                )
                Transport.send(message)
                sendingMessageSuccess.postValue("Booking is Done Successfully You will Receive A Message Shortly ")
            } catch (e: MessagingException) {
                sendingMessageFauiler.postValue("Something happened Wrong Try Again")
            }
        }
    }

    fun sendEmailToPlace(
        reciverEmail: String,
        time: String,
        date: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val prop = Properties()
            prop["mail.smtp.host"] = "smtp.gmail.com"
            prop["mail.smtp.socketFactory.port"] = "465"
            prop["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            prop["mail.smtp.auth"] = "true"
            prop["mail.smtp.port"] = "465"
            val session =
                javax.mail.Session.getDefaultInstance(prop, object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Constants.APP_EMAIL, Constants.APP_PASSWORD)
                    }
                })
            try {
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(Constants.APP_EMAIL))
                message.setRecipients(
                    javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(reciverEmail)
                )

                message.subject = "You Have A Client ..  "
                message.setText(
                    "hello," + System.lineSeparator() + "  ${userPreferences.name}  is Book In time $time In Day $date " + System.lineSeparator() + "Contact With Him For More Details " + userPreferences.email
                )
                Transport.send(message)
                sendingMessageSuccess.postValue("Booking is Done Successfully You will Receive A Message Shortly ")
            } catch (e: MessagingException) {
                sendingMessageFauiler.postValue("Something happened Wrong Try Again")
            }
        }
    }
}