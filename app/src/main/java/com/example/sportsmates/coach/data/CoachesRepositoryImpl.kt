package com.example.sportsmates.coach.data

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.example.sportsmates.coach.domain.CoachesRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CoachesRepositoryImpl : CoachesRepository {
    override suspend fun getCoachImages(coachId: String?): List<StorageReference?> {
        return withContext(IO) {
            val listOfCoachImages = mutableListOf<StorageReference?>()
            val listReference =
                FirebaseStorage.getInstance().reference.child("/coach/$coachId")
            listReference.listAll().addOnSuccessListener { listResult ->
                Log.d(ContentValues.TAG, listResult.items.toString())
                listOfCoachImages.addAll(listResult.items)
            }
            listOfCoachImages
        }

    }

    override suspend fun getCoachMainImage(coachId: String?): Uri? {
        return withContext(IO)
        {
            var coachImage: Uri? = null
            try {
                val storageReference =
                    FirebaseStorage.getInstance().reference.child("/coach/$coachId/main.jpg")
                storageReference.downloadUrl.addOnSuccessListener { mainImage ->
                    coachImage = mainImage
                }.await()
            } catch (ex: Throwable) {
            }
            coachImage
        }

    }

    override suspend fun getAllCoaches(listOfSports: List<String>?): List<Coach?> {
        return withContext(IO)
        {
            val listOfCoaches = mutableListOf<Coach?>()
            FirebaseDatabase.getInstance().getReference("Coach").get()
                .addOnSuccessListener { data ->
                    val coaches = data.children
                    coaches.forEach {
                        listOfCoaches.add(it.getValue(Coach::class.java))
                    }

                }.await()
            listOfCoaches
        }

    }

}