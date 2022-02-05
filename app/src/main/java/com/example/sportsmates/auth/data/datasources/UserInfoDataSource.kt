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

class UserInfoDataSource(
    private val userAuth: FirebaseAuth
) {

    suspend fun login(email: String, password: String): Flow<Boolean> = flow {
        emit(userAuth.signInWithEmailAndPassword(email, password).await().user != null)
    }.flowOn(Dispatchers.IO)


    suspend fun fetchUserData(): Flow<User?> = flow {
        emit(
            FirebaseDatabase.getInstance().getReference("Users")
                .child(userAuth.currentUser.uid).get().await().getValue(User::class.java)
        )
    }.flowOn(Dispatchers.IO)


    suspend fun signUp(user: User, saveUser: suspend (User) -> Boolean?): Flow<Boolean?> =
        flow {
            if (userAuth.createUserWithEmailAndPassword(user.email!!, user.password!!)
                    .await().user != null
            ) emit(saveUser(user))
        }.flowOn(Dispatchers.IO)


    suspend fun addUserToDataBase(user: User, uploadImage: suspend (Uri) -> Boolean): Boolean? {
        val image = user.userImage
        user.userImage = null
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userAuth.currentUser.uid)
            .setValue(user).await()
        return image?.let { uploadImage(it) }
    }


    suspend fun uploadUserImage(filePath: Uri): Boolean {
        return FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)
            .putFile(filePath).await().uploadSessionUri != null
    }


    suspend fun logout() {
        userAuth.signOut()
    }

    suspend fun retrieveUserImage(): Uri? {
        return FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid).downloadUrl.await()

    }

    suspend fun updateUserName(name: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserEmail(email: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserPassword(password: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserCity(city: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserAge(age: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserBio(bio: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserSportsList(sports: List<String>): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserAuthenticationEmail(
        newEmail: String,
        oldEmail: String,
        password: String
    ): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    suspend fun updateUserAuthenticationPassword(
        newPassword: String,
        oldPassword: String,
        email: String
    ): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}