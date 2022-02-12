package com.example.sportsmates.coach.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.coach.data.Coach
import com.example.sportsmates.coach.domain.CoachUseCase
import com.example.sportsmates.networking.Resource
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

typealias Coaches = List<Coach?>
typealias CoachesImages = List<StorageReference?>

class CoachViewModel(private val coachUseCase: CoachUseCase) : ViewModel() {

    private val _listOfSCoachesState = MutableStateFlow<Resource<Coaches>>(Resource.Loading)
    val listOfSCoachesState get() = _listOfSCoachesState.asStateFlow()
    private val _listOfCoachesImagesState =
        MutableStateFlow<Resource<CoachesImages>>(Resource.Loading)
    val listOfSCoachesImagesState get() = _listOfCoachesImagesState.asStateFlow()


    init {
        getCoachesList()
    }

    private fun getCoachesList() {
        viewModelScope.launch {
            try {
                _listOfSCoachesState.emit(
                    Resource.Success(
                        coachUseCase.getRelatedCoaches()
                    )
                )
            } catch (throwable: Throwable) {
                _listOfSCoachesState.emit(
                    Resource.Error(
                        throwable = throwable
                    )
                )
            }

        }
    }

    fun getCoachesImagesList(coachId: String?) {

        viewModelScope.launch {
            try {
                _listOfCoachesImagesState.emit(
                    Resource.Success(
                        data = coachUseCase.getCoachListOfImages(
                            coachId
                        )
                    )
                )

            } catch (throwable: Throwable) {
                _listOfCoachesImagesState.emit(
                    Resource.Error(
                        throwable = throwable
                    )
                )
            }

        }
    }


/*
    private suspend fun getRelatedCoaches(
        listOfSports: MutableList<String>?
    ): List<Coach>? {
        FirebaseDatabase.getInstance().getReference("Coach").get().addOnSuccessListener { data ->
            val children = data.children
            children.forEach { it ->
                val coach: Coach? = it.getValue(Coach::class.java)
                if (listOfSports!!.contains(coach?.sportName)) {
                    listOfSCoaches?.add(coach!!)
                }
            }
        }.await()
        return listOfSCoaches
    }

    private suspend fun retrievePhoto(coachId: String?): MutableList<StorageReference>? {
        var listOfSCoachesImages: MutableList<StorageReference>? = mutableListOf()

        val listReference =
            FirebaseStorage.getInstance().reference.child("/coach/$coachId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(TAG, listResult.items.toString())
            listOfSCoachesImages = listResult.items
        }.await()

        return listOfSCoachesImages


    }


    fun retrievePhotoDetails(coachId: String?) {
        val listReference =
            FirebaseStorage.getInstance().reference.child("/coach/$coachId")

        listReference.listAll().addOnSuccessListener { listResult ->
            Log.d(TAG, listResult.items.toString())
            _listOfCoachesImagesEvent.postValue(listResult.items)
        }


    }


    private suspend fun getUserSportsList(): MutableList<String>? {
        var listOfSports: MutableList<String>? = mutableListOf()
        Firebase.auth.currentUser?.let {
            FirebaseDatabase.getInstance().getReference("Users")
                .child(it.uid)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "getUser:Success")
                        val user: User? = task.result?.getValue(User::class.java)
                        listOfSports = user?.sportsList?.toMutableList()

                    } else {
                        Log.d(TAG, "getUser:Failed", task.exception)
                    }
                }.await()
        }
        return listOfSports
    }*/

}
