package com.example.sportsmates.auth.domain.usecase

import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.auth.domain.helpers.SignInError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class UserUseCase(private val userRepository: UserRepository) {

    suspend fun login(email: String, password: String): Flow<Boolean> {
       return userRepository.login(email, password)
           .catch {
               throw SignInError(it.message!!)
           }
    }
}