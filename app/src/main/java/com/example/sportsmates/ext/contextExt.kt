package com.example.sportsmates.ext

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


fun Context.getEncryptedSharedPreferences(fileName: String): SharedPreferences {
    val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    return EncryptedSharedPreferences
        .create(
            fileName,
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
}
val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)