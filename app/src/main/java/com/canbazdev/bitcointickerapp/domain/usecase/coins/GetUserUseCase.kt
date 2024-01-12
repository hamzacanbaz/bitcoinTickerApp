package com.canbazdev.bitcointickerapp.domain.usecase.coins

import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    operator fun invoke() = firebaseRepository.getCurrentUser()
}