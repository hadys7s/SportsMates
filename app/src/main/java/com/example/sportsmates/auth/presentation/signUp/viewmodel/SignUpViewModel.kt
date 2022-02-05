package com.example.sportsmates.auth.presentation.signUp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.auth.data.model.User
import com.example.sportsmates.auth.domain.usecase.UserUseCase
import com.example.sportsmates.networking.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SignUpViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val input = User()

    private val _signUpState = MutableSharedFlow<Resource<Boolean>>()
    val signUpState get() = _signUpState.asSharedFlow()

    fun signUp() = viewModelScope.launch {
        userUseCase.signUp(input)
            .onStart {
                _signUpState.emit(Resource.Loading)
            }
            .catch {
                _signUpState.emit(Resource.Error(it))
            }
            .collect {
                if (it != null && it == true) {
                    _signUpState.emit(Resource.Success(it))
                }
            }
    }

    fun initInput(user: User, step: SignUpSteps) {
        when (step) {
            SignUpSteps.STEP_ONE -> {
                input.name = user.name
                input.email = user.email
                input.password = user.password
                input.userImage = user.userImage
            }
            SignUpSteps.STEP_TWO -> {
                input.city = user.city
                input.age = user.age
                input.gender = user.gender
            }
            SignUpSteps.STEP_THREE -> {
                input.sportsList = user.sportsList
            }
        }
    }

    fun getInfo(): User {
        return input
    }
}

enum class SignUpSteps {
    STEP_ONE,
    STEP_TWO,
    STEP_THREE
}
