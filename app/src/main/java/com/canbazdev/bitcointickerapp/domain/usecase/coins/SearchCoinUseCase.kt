package com.canbazdev.bitcointickerapp.domain.usecase.coins

import com.canbazdev.bitcointickerapp.domain.repository.CoinsRepository
import javax.inject.Inject

class SearchCoinUseCase @Inject constructor(
    private val coinRepository: CoinsRepository
) {
    operator fun invoke(searchQuery: String) = coinRepository.searchCoin(searchQuery)

}