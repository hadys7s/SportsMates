package com.example.sportsmates.auth.data.datasources

import android.net.Uri
import com.example.sportsmates.auth.data.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
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

    suspend fun updateUserName(name: String): Flow<Boolean?> = flow {
        val user =
            FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid)
                .get()
        if (user.isSuccessful) {
            emit(user.result?.child("name")?.ref?.setValue(name)?.isSuccessful)
        }

    }

    suspend fun updateUserEmail(email: String): Flow<Boolean?> = flow {
        val user =
            FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid)
                .get()
        if (user.isSuccessful) {
            emit(user.result?.child("email")?.ref?.setValue(email)?.isSuccessful)
        }
    }

    suspend fun updateUserPassword(password: String): Flow<Boolean?> = flow {
        val user =
            FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid)
                .get()
        if (user.isSuccessful) {
            emit(user.result?.child("password")?.ref?.setValue(password)?.isSuccessful)
        }
    }

    suspend fun updateUserCity(city: String): Flow<Boolean?> = flow {
        val user =
            FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid)
                .get()
        if (user.isSuccessful) {
            emit(user.result?.child("city")?.ref?.setValue(city)?.isSuccessful)
        }
    }

    suspend fun updateUserAge(age: String): Flow<Boolean?> = flow {
        val user =
            FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid)
                .get()
        if (user.isSuccessful) {
            emit(user.result?.child("age")?.ref?.setValue(age)?.isSuccessful)
        }
    }

    suspend fun updateUserBio(bio: String): Flow<Boolean?> = flow {
        val user =
            FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid)
                .get()
        if (user.isSuccessful) {
            emit(user.result?.child("bio")?.ref?.setValue(bio)?.isSuccessful)
        }
    }

    suspend fun updateUserSportsList(sports: List<String>): Flow<Boolean?> = flow {
        val user =
            FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid)
                .get()
        if (user.isSuccessful) {
            emit(user.result?.child("sportsList")?.ref?.setValue(sports)?.isSuccessful)
        }
    }

    suspend fun updateUserAuthenticationEmail(
        newEmail: String,
        oldEmail: String?,
        password: String
    ): Flow<Boolean?> = flow {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val credential: AuthCredential = EmailAuthProvider.getCredential(oldEmail, password)
        if (user?.reauthenticate(credential)?.isSuccessful == true) {
            emit(user.updateEmail(newEmail).isSuccessful)
        }
    }

    suspend fun updateUserAuthenticationPassword(
        newPassword: String,
        oldPassword: String,
        email: String?
    ): Flow<Boolean> = flow {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val credential: AuthCredential = EmailAuthProvider.getCredential(email, oldPassword)
        if (user?.reauthenticate(credential)?.isSuccessful == true) {
            emit(user.updatePassword(newPassword).isSuccessful)
        }
    }

    suspend fun deleteUser(): Flow<Boolean> = flow {
        emit(Firebase.auth.currentUser.delete().isSuccessful)
    }

    suspend fun deleteUserImage(): Flow<Boolean?> = flow {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)
        emit(storageReference.delete().isSuccessful)
    }
}
