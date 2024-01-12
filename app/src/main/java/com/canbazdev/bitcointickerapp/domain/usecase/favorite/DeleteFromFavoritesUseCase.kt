package com.canbazdev.bitcointickerapp.domain.usecase.favorite

import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class DeleteFromFavoritesUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(favouritesUI: CoinDetail) =
        firebaseRepository.deleteFromFavourites(favouritesUI)
}