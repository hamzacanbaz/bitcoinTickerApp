package com.canbazdev.bitcointickerapp.domain.usecase

import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository

import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    operator fun invoke(email: String, password: String) =
        firebaseRepository.registerWithEmailAndPassword(email, password)


}