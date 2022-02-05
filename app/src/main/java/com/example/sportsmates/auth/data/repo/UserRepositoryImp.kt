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

class UserRepositoryImp(
    private val dataSource: UserInfoDataSource,
    private val userPref: UserPreferences
) : UserRepository {

    @FlowPreview
    override suspend fun login(email: String, password: String): Flow<Boolean> {
        return dataSource.login(email, password).flatMapConcat {
            dataSource.fetchUserData().flatMapConcat { user ->
                userPref.user = user
                flowOf(it)
            }
        }
    }

    @FlowPreview
    override suspend fun signUp(user: User): Flow<Boolean?> {
        return dataSource.signUp(user,::saveUser).flatMapConcat {
            userPref.user = user
            flowOf(it)
        }
    }

    override suspend fun logout() {
        dataSource.logout()
        userPref.clear()
    }

    @FlowPreview
    override suspend fun getUserInfo(): Flow<User?> {
        return dataSource.fetchUserData().flatMapConcat {
            val image = dataSource.retrieveUserImage()
            it?.userImage = image
            flowOf(it)
        }
    }

    override suspend fun updateUserName(name: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserEmail(email: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserPassword(password: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserCity(city: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserAge(age: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserBio(bio: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserSportsList(sports: List<String>): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserAuthenticationEmail(
        newEmail: String,
        oldEmail: String,
        password: String
    ): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserAuthenticationPassword(
        newPassword: String,
        oldPassword: String,
        email: String
    ): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    private suspend fun uploadImage(filePath: Uri):Boolean {
       return dataSource.uploadUserImage(filePath)
    }

    private suspend fun saveUser(user: User):Boolean? {
         return dataSource.addUserToDataBase(user, ::uploadImage)
    }
}