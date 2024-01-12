package com.canbazdev.bitcointickerapp.domain.usecase.coins

import com.canbazdev.bitcointickerapp.domain.repository.CoinsRepository
import javax.inject.Inject

class GetCoinMarketUseCase @Inject constructor(
    private val coinRepository: CoinsRepository
) {
    operator fun invoke() = coinRepository.coinsMarkets()

}