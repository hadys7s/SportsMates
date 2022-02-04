package com.example.sportsmates.auth.domain.datainterfaces

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(email: String, password: String): Flow<Boolean>
}