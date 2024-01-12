package com.canbazdev.bitcointickerapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.usecase.LoginUseCase
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<AuthResult>>(Resource.Loading)
    val loginFlow = _loginFlow.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        loginUseCase.invoke(email, password).collect {
            _loginFlow.emit(it)
        }
    }


}