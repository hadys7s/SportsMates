package com.example.sportsmates

import android.content.Context
import androidx.core.content.edit
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.ext.fromJson
import com.example.sportsmates.ext.getEncryptedSharedPreferences
import com.example.sportsmates.ext.toJson

class UserPreferences(context: Context) {

    private val userPreference = context.getEncryptedSharedPreferences(USER_PREFERENCE_NAME)

    var user: User?
        get() = userPreference.getString(USER, null)?.fromJson()
        set(value) {
            userPreference.edit {
                putString(USER, value.toJson())
            }
        }

    fun clear() {
        userPreference.edit().clear().apply()
    }
    companion object {
        const val USER_PREFERENCE_NAME = "user_preferences"
        private const val USER: String = "USER"
    }
}
