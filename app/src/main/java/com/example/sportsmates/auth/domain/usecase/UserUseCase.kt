package com.example.sportsmates.auth.domain.usecase

import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import com.example.sportsmates.auth.domain.helpers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

class UserUseCase(private val userRepository: UserRepository) {

    suspend fun login(email: String, password: String): Flow<Boolean> {
        return userRepository.login(email, password).flatMapConcat {
            userRepository.getUserInfo().flatMapConcat { user ->
                userRepository.cashUser(user)
                flowOf(user)
            }
            flowOf(it)
        }.catch {
            throw SignInError(it.message!!)
        }
    }

    suspend fun signUp(user: User): Flow<Boolean?> {
        return userRepository.signUp(user).flatMapConcat {
            userRepository.cashUser(user)
            flowOf(it)
        }.catch {
            throw SignUpError(it.message!!)
        }
    }


    suspend fun updateUserName(name: String): Flow<Boolean?> {
        return userRepository.updateUserName(name)
            .catch {
                throw UpdateNameError(it.message!!)
            }
    }

    suspend fun updateUserCity(city: String): Flow<Boolean?> {
        return userRepository.updateUserCity(city)
            .catch {
                throw UpdateCityError(it.message!!)
            }
    }

    suspend fun updateUserAge(age: String): Flow<Boolean?> {
        return userRepository.updateUserAge(age)
            .catch {
                throw UpdateAgeError(it.message!!)
            }
    }

    suspend fun updateUserBio(bio: String): Flow<Boolean?> {
        return userRepository.updateUserBio(bio)
            .catch {
                throw UpdateBioError(it.message!!)
            }
    }

    suspend fun updateUserSportsList(sports: List<String>): Flow<Boolean?> {
        return userRepository.updateUserSportsList(sports)
            .catch {
                throw UpdateSportsError(it.message!!)
            }
    }

    suspend fun updateUserEmail(
        newEmail: String,
        oldEmail: String,
        password: String
    ): Flow<Boolean?> {
        return userRepository.updateUserEmail(newEmail, oldEmail, password).flatMapConcat {
            val user = userRepository.getCashedUser()
            user?.email = newEmail
            userRepository.cashUser(user)
            flowOf(it)
        }.catch {
            throw UpdateEmailError(it.message!!)
        }
    }

    suspend fun updateUserPassword(
        newPassword: String,
        oldPassword: String,
        email: String
    ): Flow<Boolean?> {
        return userRepository.updateUserPassword(newPassword, oldPassword, email).flatMapConcat {
            val user = userRepository.getCashedUser()
            user?.email = newPassword
            userRepository.cashUser(user)
            flowOf(it)
        }.catch {
            throw UpdatePasswordError(it.message!!)
        }
    }

    suspend fun deleteUser(): Flow<Boolean?> =
        userRepository.deleteUser()


    suspend fun getUserInfo(): Flow<User?> =
        userRepository.getUserInfo()


}