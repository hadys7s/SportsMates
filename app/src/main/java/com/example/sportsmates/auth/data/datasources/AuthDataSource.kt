package com.example.sportsmates.auth.data.datasources

import android.net.Uri
import com.example.sportsmates.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
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
    }.flowOn(Dispatchers.IO)

    suspend fun signUp(user: User, saveUser: suspend (User) -> Unit): Flow<Boolean> =
        flow {
            var signUpSuccess = false
            userAuth.createUserWithEmailAndPassword(user.email!!, user.password!!)
                .addOnSuccessListener {
                    signUpSuccess = true
                }.await()
            if (signUpSuccess)
                saveUser(user)
            emit(signUpSuccess)
        }.flowOn(Dispatchers.IO)

    suspend fun addUserToDataBase(user: User, uploadImage: suspend (Uri) -> Unit) {
        val image = user.userImage
        user.userImage = null
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userAuth.currentUser.uid)
            .setValue(user).await()
        image?.let { it1 -> uploadImage(it1) }
    }

    suspend fun uploadUserImage(filePath: Uri) {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)
        storageReference.putFile(filePath).await()
    }
}