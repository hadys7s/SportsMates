package com.example.sportsmates.auth.domain.datainterfaces

import com.example.sportsmates.auth.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(email: String, password: String): Flow<Boolean>

    suspend fun signUp(user: User):Flow<Boolean>?
}