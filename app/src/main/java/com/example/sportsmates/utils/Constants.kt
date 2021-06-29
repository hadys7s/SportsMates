package com.example.sportsmates.utils

object Constants {
    const val NEWS_API_KEY_NAME: String = "apiKey"
    const val NEWS_API_KEY_VALUE: String = "61c0cafbfa764c3798af26e65f9d7393"
    const val NUTRO_API_KEY_NAME: String = "x-app-key"
    const val NUTRO_API_KEY_VALUE: String = "a9042be222b4fbe354592a5a5618505d"
    const val NUTRO_APP_ID_KEY_NAME: String = "x-app-id"
    const val NUTRO_APP_ID_KEY_VALUE: String = "73c21e2a"
    const val NEWS_BASE_URL = "https://newsapi.org/v2/"
     const val NUTRO_BASE_URL = "https://trackapi.nutritionix.com/v2/"
    const val MESSAGE_TYPE_LEFT = 0
    const val MESSAGE_TYPE_RIGHT = 1
    const val _NAME="Name"
    const val _SPORT="Sports"
    const val _CITY="City"
    const val _MAIL="Mail"
    const val _BIO="Bio"
    const val _PASSWORD="Password"
    const val APP_EMAIL="sportsmates02@gmail.com"
    const val APP_PASSWORD="sportsmates111222"
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
enum class AppPlaceHolders{
    USER,
    COUCH,
    PLACE,
    EVENTS
}