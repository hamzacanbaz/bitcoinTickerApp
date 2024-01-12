package com.canbazdev.bitcointickerapp.domain.repository

import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.canbazdev.bitcointickerapp.domain.model.CoinList
import com.canbazdev.bitcointickerapp.domain.model.CoinMarkets
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface CoinsRepository {
    fun coinsMarkets(): Flow<Resource<List<CoinMarkets>>>

    fun coinList(): Flow<Resource<List<CoinList>>>

    fun searchCoin(searchQuery: String): Flow<Resource<List<CoinList>>>

    fun coinById(coinId: String): Flow<Resource<CoinDetail>>

    fun currentPriceById(period: Duration, coinId: String): Flow<Resource<Double>>
}