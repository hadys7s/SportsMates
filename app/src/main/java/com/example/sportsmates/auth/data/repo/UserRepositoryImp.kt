package com.example.sportsmates.auth.data.repo

import com.example.sportsmates.UserPreferences
import com.example.sportsmates.auth.data.datasources.AuthDataSource
import com.example.sportsmates.auth.domain.datainterfaces.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

class UserRepositoryImp(
    private val dataSource: AuthDataSource,
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
}