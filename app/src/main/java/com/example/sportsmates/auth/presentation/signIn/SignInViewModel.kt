package com.example.sportsmates.auth.presentation.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.auth.domain.usecase.UserUseCase
import com.example.sportsmates.networking.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignInViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _loginState = MutableSharedFlow<Resource<Boolean>>()
    val loginState get() = _loginState.asSharedFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        userUseCase.login(email, password)
            .onStart {
                _loginState.emit(Resource.Loading)
            }
            .catch {
                _loginState.emit(Resource.Error(it))
            }
            .collect {
                _loginState.emit(Resource.Success(it))
            }
    }

}
