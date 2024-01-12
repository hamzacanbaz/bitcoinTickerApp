package com.canbazdev.bitcointickerapp.domain.usecase

import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository

import javax.inject.Inject

class LoginUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    operator fun invoke(email: String, password: String) =
        firebaseRepository.loginWithEmailAndPassword(email, password)


}