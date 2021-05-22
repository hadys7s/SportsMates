package com.example.sportsmates.utils

object Constants {
    const val API_KEY_VALUE: String = "61c0cafbfa764c3798af26e65f9d7393"
    const val API_KEY_NAME: String = "apiKey"
    const val MESSAGE_TYPE_LEFT = 0
    const val MESSAGE_TYPE_RIGHT = 1
    const val _NAME="Name"
    const val _SPORT="Sports"
    const val _CITY="City"
    const val _MAIL="Mail"
    const val _BIO="Bio"
    const val _PASSWORD="Password"
    const val USER="User"
    const val HINT="Hint"
    const val EDIT="Edit"
}

enum class TargetActivity {
    CONTACTS_DETAILS,
    MESSAGE_ACTIVITY
}
enum class InfoType{
    SPORTS,
    CITY,
    NAME,
    MAIL,
    PASSWORD,
    BIO
}