package com.canbazdev.bitcointickerapp.domain.usecase


import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    operator fun invoke() = firebaseRepository.signOut()

}