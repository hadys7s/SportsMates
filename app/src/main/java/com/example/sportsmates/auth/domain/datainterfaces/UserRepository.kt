package com.example.sportsmates.auth.domain.datainterfaces

import com.example.sportsmates.auth.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(email: String, password: String): Flow<Boolean>

    suspend fun signUp(user: User): Flow<Boolean?>
    suspend fun logout()
    suspend fun getUserInfo(): Flow<User?>

    suspend fun updateUserName(name: String): Flow<Boolean?>
    suspend fun updateUserCity(city: String): Flow<Boolean?>
    suspend fun updateUserAge(age: String): Flow<Boolean?>
    suspend fun updateUserBio(bio: String): Flow<Boolean?>
    suspend fun updateUserSportsList(sports: List<String>): Flow<Boolean?>
    suspend fun updateUserEmail(
        newEmail: String,
        oldEmail: String,
        password: String
    ): Flow<Boolean?>

    suspend fun updateUserPassword(
        newPassword: String,
        oldPassword: String,
        email: String
    ): Flow<Boolean?>

    suspend fun cashUser(user: User?)
    suspend fun getCashedUser():User?

    suspend fun deleteUser():Flow<Boolean?>
}