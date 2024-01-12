package com.canbazdev.bitcointickerapp.domain.usecase.favorite

import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository

import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke() = firebaseRepository.getFavourites()

}