package com.canbazdev.bitcointickerapp.domain.model

data class CoinMarkets(
    val id: Int?,
    val coinId: String?,
    val currentPrice: Double?,
    val image: String?,
    val name: String?,
    val priceChangePercentage24h: Double?
)
