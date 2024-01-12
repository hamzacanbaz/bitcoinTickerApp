package com.canbazdev.bitcointickerapp.domain.source

import com.canbazdev.bitcointickerapp.data.model.coinlist.CoinListEntity
import com.canbazdev.bitcointickerapp.data.model.coinmarket.CoinMarketEntity


interface LocalDataSource {
    suspend fun insertCoinList(items: List<CoinListEntity>)

    suspend fun insertCoinMarketsList(items: List<CoinMarketEntity>)

    suspend fun getCoinList(): List<CoinListEntity>

    suspend fun getCoinMarkets(): List<CoinMarketEntity>

    suspend fun searchCoin(searchQuery: String): List<CoinListEntity>

    suspend fun deleteCoinList()

    suspend fun deleteCoinMarkets()
}