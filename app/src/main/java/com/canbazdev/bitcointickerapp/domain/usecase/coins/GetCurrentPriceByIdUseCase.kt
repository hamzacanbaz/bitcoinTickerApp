package com.canbazdev.bitcointickerapp.domain.usecase.coins

import com.canbazdev.bitcointickerapp.domain.repository.CoinsRepository

import javax.inject.Inject
import kotlin.time.Duration

class GetCurrentPriceByIdUseCase @Inject constructor(
    private val coinRepository: CoinsRepository
) {
    operator fun invoke(period: Duration, coinId: String) =
        coinRepository.currentPriceById(period, coinId)

}