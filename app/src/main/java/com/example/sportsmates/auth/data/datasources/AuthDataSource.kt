package com.example.sportsmates.auth.data.datasources

import com.example.sportsmates.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class AuthDataSource(
    private val userAuth: FirebaseAuth
) {

    suspend fun login(email: String, password: String): Flow<Boolean> = flow {
        var loginSuccess = false
        userAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            loginSuccess = true
        }.await()
        emit(loginSuccess)
    }.flowOn(Dispatchers.IO)

    suspend fun fetchUserData(): Flow<User?> = flow {
        var user: User? = null
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userAuth.currentUser.uid)
            .get().addOnSuccessListener {
                user = it.getValue(User::class.java)
            }.await()
        emit(user)
    }
}