package com.example.sportsmates

import android.content.Context
import androidx.core.content.edit
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.ext.fromJson
import com.example.sportsmates.ext.getEncryptedSharedPreferences
import com.example.sportsmates.ext.toJson

class UserPreferences(context: Context) {

    private val userPreference = context.getEncryptedSharedPreferences(USER_PREFERENCE_NAME)

    var name: String?
        get() = userPreference.getString(USER_NAME, null)
        set(value) {
            userPreference.edit {
                putString(USER_NAME, value)
            }
        }
    var email: String?
        get() = userPreference.getString(USER_EMAIL, null)
        set(value) {
            userPreference.edit {
                putString(USER_EMAIL, value)
            }
        }

    var image: String?
        get() = userPreference.getString(USER_IMAGE, null)
        set(value) {
            userPreference.edit {
                putString(USER_IMAGE, value)
            }
        }

    var city: String?
        get() = userPreference.getString(USER_CITY, null)
        set(value) {
            userPreference.edit {
                putString(USER_CITY, value)
            }
        }

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

    fun removeItem(key: String) {
        userPreference.edit().remove(key).apply()
    }

    companion object {
        const val USER_PREFERENCE_NAME = "user_preferences"
        private const val USER_NAME: String = "USER_NAME"
        private const val USER_IMAGE: String = "USER_IMAGE"
        private const val USER_CITY: String = "CURRENT_USER_FIRST_NAME"
        private const val USER_EMAIL: String = "USER_EMAIL"
        private const val USER: String = "USER"
    }
}
