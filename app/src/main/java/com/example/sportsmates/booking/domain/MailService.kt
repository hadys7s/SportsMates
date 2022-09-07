package com.example.sportsmates.booking.domain

import com.example.sportsmates.booking.data.BookingModel
import com.example.sportsmates.utils.Constants
import java.util.*
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailService {

    private fun configMailSession(): Session {
        val prop = Properties()
        prop["mail.smtp.host"] = "smtp.gmail.com"
        prop["mail.smtp.socketFactory.port"] = "465"
        prop["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        prop["mail.smtp.auth"] = "true"
        prop["mail.smtp.port"] = "465"
        val session =
            Session.getDefaultInstance(prop, object : javax.mail.Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(Constants.APP_EMAIL, Constants.APP_PASSWORD)
                }
            })
        return session
    }

    fun sendmail(
        bookingModel: BookingModel,
        onSuccessListener: (() -> Unit),
        onFailureListener: (() -> Unit)
    ) {
        try {
            val message = MimeMessage(configMailSession())
            message.setFrom(InternetAddress(Constants.APP_EMAIL))
            message.setRecipients(
                javax.mail.Message.RecipientType.TO,
                InternetAddress.parse(bookingModel.receiverMail)
            )
            message.subject = bookingModel.mailSubject
            message.setText(bookingModel.mailBody)
            Transport.send(message)
            onSuccessListener.invoke()
        } catch (e: MessagingException) {
            onFailureListener.invoke()
        }

    }
}
