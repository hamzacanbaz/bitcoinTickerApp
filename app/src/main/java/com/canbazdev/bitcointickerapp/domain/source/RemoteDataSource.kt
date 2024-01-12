package com.canbazdev.bitcointickerapp.domain.source

import com.canbazdev.bitcointickerapp.data.model.coinDetail.CoinDetailResponse
import com.canbazdev.bitcointickerapp.data.model.coinList.CoinListResponse
import com.canbazdev.bitcointickerapp.data.model.coinMarket.CoinMarketResponse

interface RemoteDataSource {
    suspend fun getCoinList(): List<CoinListResponse>

    suspend fun getCoinMarkets(): List<CoinMarketResponse>

    suspend fun getCoinById(coinId: String): CoinDetailResponse
}