package com.canbazdev.bitcointickerapp.data.model.coinList

data class CoinListResponse(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val platforms: Map<String, String>?
)