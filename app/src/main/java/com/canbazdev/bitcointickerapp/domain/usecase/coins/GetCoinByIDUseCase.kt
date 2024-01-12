package com.canbazdev.bitcointickerapp.domain.usecase.coins

import com.canbazdev.bitcointickerapp.domain.repository.CoinsRepository

import javax.inject.Inject

class GetCoinByIDUseCase @Inject constructor(
    private val coinRepository: CoinsRepository
) {
    operator fun invoke(coinId: String) = coinRepository.coinById(coinId)

}