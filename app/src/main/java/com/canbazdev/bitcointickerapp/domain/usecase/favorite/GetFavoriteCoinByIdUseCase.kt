package com.canbazdev.bitcointickerapp.domain.usecase.favorite

import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository

import javax.inject.Inject

class GetFavoriteCoinByIdUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(coinId:String) = firebaseRepository.getFavouriteCoinById(coinId)

}