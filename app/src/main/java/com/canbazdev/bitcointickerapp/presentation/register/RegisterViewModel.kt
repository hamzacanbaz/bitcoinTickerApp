package com.canbazdev.bitcointickerapp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.usecase.RegisterUseCase
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _registerFlow = MutableStateFlow<Resource<AuthResult>>(Resource.Loading)
    val registerFlow = _registerFlow.asStateFlow()

    fun register(email: String, password: String) = viewModelScope.launch {
        registerUseCase.invoke(email, password).collect {
            _registerFlow.emit(it)
        }
    }


}