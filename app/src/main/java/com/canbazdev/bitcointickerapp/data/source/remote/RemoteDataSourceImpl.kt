package com.canbazdev.bitcointickerapp.data.source.remote

import com.canbazdev.bitcointickerapp.domain.source.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val coinService: BitcoinTickerService
) : RemoteDataSource {

    override suspend fun getCoinList() = coinService.getCoinList()

    override suspend fun getCoinMarkets() = coinService.getCoinMarkets()

    override suspend fun getCoinById(coinId: String) = coinService.getCoinById(coinId)

}