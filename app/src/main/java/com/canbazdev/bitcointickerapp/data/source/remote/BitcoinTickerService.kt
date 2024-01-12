package com.canbazdev.bitcointickerapp.data.source.remote

import com.canbazdev.bitcointickerapp.data.model.coinDetail.CoinDetailResponse
import com.canbazdev.bitcointickerapp.data.model.coinlist.CoinListEntity
import com.canbazdev.bitcointickerapp.data.model.coinlist.CoinListResponse
import com.canbazdev.bitcointickerapp.data.model.coinmarket.CoinMarketEntity
import com.canbazdev.bitcointickerapp.data.model.coinmarket.CoinMarketResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BitcoinTickerService {

    @GET("coins/list")
    suspend fun getCoinList(): List<CoinListResponse>

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&locale=en")
    suspend fun getCoinMarkets(): List<CoinMarketResponse>

    @GET("coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
    suspend fun getCoinById(@Path("id") coinId: String): CoinDetailResponse

}