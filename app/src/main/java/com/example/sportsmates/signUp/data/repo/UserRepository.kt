package com.example.sportsmates.signUp.data.repo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportsmates.UserPreferences
import com.example.sportsmates.ext.getCurrentUserID
import com.example.sportsmates.signUp.data.model.User
import com.example.sportsmates.utils.SingleLiveEvent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class UserRepository(
    private val userAuth: FirebaseAuth,
    private val userpref: UserPreferences
) {

    var signUpAuthSuccess = SingleLiveEvent<Any>()
    var signUpAuthFailed = MutableLiveData<String>()
    var uploadImageFailed = MutableLiveData<String>()
    var uploadImageSucess = MutableLiveData<String>()
    var signUpSuccess = SingleLiveEvent<Any>()
    var signUpFailed = MutableLiveData<String>()
    var retriveImage = MutableLiveData<Uri>()
    var loginFailed = MutableLiveData<String>()
    var loginSuccess = SingleLiveEvent<Any>()
    var userData = MutableLiveData<User?>()
    var updateInfoSuccess = MutableLiveData<String>()
    var updateInfoFailuer = MutableLiveData<String>()

    fun login(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginSuccess.postValue(userAuth.currentUser.uid)

                } else {
                    loginFailed.postValue(task.exception?.message)
                    Log.w(TAG, "loginFailed:failure", task.exception)
                }

            }

    }

    fun logout() {
        userpref.clear()
        userAuth.signOut()
    }

    fun deleteUser() {
        Firebase.auth.currentUser.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }

            }
    }

    fun signUp(user: User?, filepath: Uri) {
        // [START create_user_with_email]
        userAuth.createUserWithEmailAndPassword(user?.email, user?.password)
            .addOnSuccessListener {
                uploadPhoto(filepath)
            }.addOnFailureListener {
                Log.w(TAG, "createUserWithEmail:failure", it)
                signUpAuthFailed.postValue(it.message.toString())
            }
    }


    fun addUserToDataBase(user: User?) {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userAuth.currentUser.uid)
            .setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "userAddedToDataBase:Success")
                    signUpSuccess.call()


                } else {
                    Log.d(TAG, "userAddedToDataBase:Failed")
                    signUpFailed.postValue(task.exception?.message)
                }
            }
    }

    fun fetchUserData() {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(Firebase.auth.currentUser.uid)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "getUser:Success")
                    val user = task.result?.getValue(User::class.java)
                    userData.postValue(user!!)
                    userpref.name = user.name
                    userpref.email=user.email
                } else {
                    Log.d(TAG, "getUser:Failed", task.exception)

                }
            }
    }

    fun uploadPhoto(filepath: Uri) {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)

        storageReference.putFile(filepath)
            .addOnSuccessListener { task ->
                Log.d(TAG, "ImageUpload:Success")
                signUpAuthSuccess.call()
                uploadImageSucess.postValue("Image Uploaded Successfully")
            }
            .addOnFailureListener {
                uploadImageFailed.postValue(it.message.toString())
            }
    }

    fun retrievePhoto() {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)
        storageReference.downloadUrl.addOnSuccessListener { imageUri ->
            Log.d(TAG, "ImageUpload:Success")
            userpref.image = imageUri.toString()
            retriveImage.postValue(imageUri)
        }
            .addOnFailureListener {
                Log.d(TAG, it.toString())
            }

    }


    fun deleteProfileImage() {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("userImages/" + userAuth.currentUser.uid)
        storageReference.delete().addOnSuccessListener {
            Log.d(TAG, "ImageUpload:Success")
        }
            .addOnFailureListener {
                Log.d(TAG, it.toString())
            }


    }
    fun updateUserBio(bio:String){
        FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid).get()
            .addOnSuccessListener { dataSnapshot ->
                dataSnapshot.child("about").ref.setValue(bio)
                updateInfoSuccess.postValue("Name Updated Successfully")
            }.addOnFailureListener {
                updateInfoFailuer.postValue(it.toString())
            }
    }
    fun updateUserName(name: String) {
        FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid).get()
            .addOnSuccessListener { dataSnapshot ->
                dataSnapshot.child("name").ref.setValue(name)
                updateInfoSuccess.postValue("Name Updated Successfully")
                userpref.name=name
            }.addOnFailureListener {
                updateInfoFailuer.postValue(it.toString())
            }
    }

   private fun updateUserEmail(email: String) {
        FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid).get()
            .addOnSuccessListener { dataSnapshot ->
                dataSnapshot.child("email").ref.setValue(email)
                updateInfoSuccess.postValue("Email Updated Successfully")
            }.addOnFailureListener {
                updateInfoFailuer.postValue(it.toString())
            }
    }

    fun updateUserCity(city: String) {
        FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid).get()
            .addOnSuccessListener { dataSnapshot ->
                dataSnapshot.child("city").ref.setValue(city)
                updateInfoSuccess.postValue("City Updated Successfully")
                userpref.city=city
            }.addOnFailureListener {
                updateInfoFailuer.postValue(it.toString())
            }
    }

   private fun updateUserPassword(password: String) {
        FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid).get()
            .addOnSuccessListener { dataSnapshot ->
                dataSnapshot.child("password").ref.setValue(password)
                updateInfoSuccess.postValue("Password Updated Successfully")
            }.addOnFailureListener {
                updateInfoFailuer.postValue(it.toString())
            }
    }

    fun updateUserSportsList(sports: List<String>) {
        FirebaseDatabase.getInstance().getReference("Users").child(userAuth.currentUser.uid).get()
            .addOnSuccessListener { dataSnapshot ->
                dataSnapshot.child("sportsList").ref.setValue(sports)
                updateInfoSuccess.postValue("Sports Updated Successfully")
            }.addOnFailureListener {
                updateInfoFailuer.postValue(it.toString())
            }
    }

    fun updateUserAuthenticationEmail(
        newEmail: String,
        oldPassword: String
    ){
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val credential: AuthCredential = EmailAuthProvider.getCredential(user!!.email, oldPassword)
        user!!.reauthenticate(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.updateEmail(newEmail).addOnCompleteListener {
                    if (it.isSuccessful){
                        updateUserEmail(newEmail)
                    }else{
                        updateInfoFailuer.postValue(it.exception.toString())
                    }
                }
            } else {
                updateInfoFailuer.postValue(task.exception.toString())
            }
        }
    }
    fun updateUserAuthenticationPassword(
        newPassword: String,
        oldPassword: String
    ){
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val credential: AuthCredential = EmailAuthProvider.getCredential(user!!.email, oldPassword)
        user!!.reauthenticate(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener {
                    if (it.isSuccessful){
                        updateUserPassword(newPassword)
                    }else{
                        updateInfoFailuer.postValue(it.exception.toString())
                    }
                }
            } else {
                updateInfoFailuer.postValue(task.exception.toString())
            }
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}