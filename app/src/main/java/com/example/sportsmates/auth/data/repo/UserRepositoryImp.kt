package com.example.sportsmates.auth.data.repo

import android.net.Uri
import com.example.sportsmates.UserPreferences
import com.example.sportsmates.auth.data.datasources.UserInfoDataSource
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last

class UserRepositoryImp(
    private val dataSource: UserInfoDataSource,
    private val userPref: UserPreferences
) : UserRepository {

    @FlowPreview
    override suspend fun login(email: String, password: String): Flow<Boolean> {
        return dataSource.login(email, password)
    }

    @FlowPreview
    override suspend fun signUp(user: User): Flow<Boolean?> {
        return dataSource.signUp(user, ::saveUser)
    }

    override suspend fun logout() {
        dataSource.logout()
        userPref.clear()
    }

    @FlowPreview
    override suspend fun getUserInfo(): Flow<User?> {
        return if (userPref.user != null) {
            flowOf(userPref.user)
        } else {
            dataSource.fetchUserData().flatMapConcat {
                val image = dataSource.retrieveUserImage()
                it?.userImage = image
                userPref.user = it
                flowOf(it)
            }
        }
    }

    override suspend fun updateUserName(name: String): Flow<Boolean?> =
        dataSource.updateUserName(name)


    override suspend fun updateUserCity(city: String): Flow<Boolean?> =
        dataSource.updateUserCity(city)


    override suspend fun updateUserAge(age: String): Flow<Boolean?> =
        dataSource.updateUserAge(age)


    override suspend fun updateUserBio(bio: String): Flow<Boolean?> =
        dataSource.updateUserBio(bio)


    override suspend fun updateUserSportsList(sports: List<String>): Flow<Boolean?> =
        dataSource.updateUserSportsList(sports)


    override suspend fun updateUserEmail(
        newEmail: String,
        password: String
    ): Flow<Boolean?> =
        dataSource.updateUserAuthenticationEmail(
            newEmail, getUserInfo().last()?.email, password
        ).flatMapConcat {
            dataSource.updateUserEmail(email = newEmail)
        }

    override suspend fun updateUserPassword(
        newPassword: String,
        oldPassword: String,
    ): Flow<Boolean?> =
        dataSource.updateUserAuthenticationPassword(
            newPassword,
            oldPassword,
            getUserInfo().last()?.email
        ).flatMapConcat {
            dataSource.updateUserPassword(newPassword)
        }


    override suspend fun uploadImage(filePath: Uri): Boolean =
        dataSource.uploadUserImage(filePath)


    private suspend fun saveUser(user: User): Boolean? =
        dataSource.addUserToDataBase(user, ::uploadImage)


    override suspend fun cashUser(user: User?) {
        userPref.user = user
    }

    override fun getCashedUser(): User? =
        userPref.user

    override suspend fun deleteUser(): Flow<Boolean?> {
        return dataSource.deleteUser().flatMapConcat {
            dataSource.deleteUserImage()
        }
    }
}