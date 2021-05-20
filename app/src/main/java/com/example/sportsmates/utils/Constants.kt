package com.example.sportsmates.utils

object Constants {
    const val API_KEY_VALUE: String = "61c0cafbfa764c3798af26e65f9d7393"
    const val API_KEY_NAME: String = "apiKey"
    const val MESSAGE_TYPE_LEFT = 0
    const val MESSAGE_TYPE_RIGHT = 1
    const val NAME_SPORT="Sports"
    const val NAME_CITY="City"
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