package com.canbazdev.bitcointickerapp.domain.source

import com.canbazdev.bitcointickerapp.data.model.coinDetail.CoinDetailResponse
import com.canbazdev.bitcointickerapp.data.model.coinlist.CoinListResponse
import com.canbazdev.bitcointickerapp.data.model.coinmarket.CoinMarketResponse

interface RemoteDataSource {
    suspend fun getCoinList(): List<CoinListResponse>

    suspend fun getCoinMarkets(): List<CoinMarketResponse>

    suspend fun getCoinById(coinId: String): CoinDetailResponse
}