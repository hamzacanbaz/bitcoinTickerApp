package com.canbazdev.bitcointickerapp.data.mappers

import com.canbazdev.bitcointickerapp.data.model.coinDetail.CoinDetailResponse
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail
import com.canbazdev.bitcointickerapp.domain.model.CoinList
import com.canbazdev.bitcointickerapp.domain.model.CoinMarkets
import com.canbazdev.bitcointickerapp.data.model.coinlist.CoinListEntity
import com.canbazdev.bitcointickerapp.data.model.coinlist.CoinListResponse
import com.canbazdev.bitcointickerapp.data.model.coinmarket.CoinMarketEntity
import com.canbazdev.bitcointickerapp.data.model.coinmarket.CoinMarketResponse
fun List<CoinListEntity>.toCoinListUI() = map {
    CoinList(
        id = it.id,
        coinId = it.coinId,
        name = it.name
    )
}

fun CoinDetailResponse.toCoinDetailUI() = CoinDetail(
    name = name ?: " ",
    coinId = id,
    hashingAlgorithm = hashingAlgorithm ?: " ",
    description = description?.en ?: " ",
    image = image?.large,
    currentPrice = marketData?.currentPrice?.usd,
    priceChangePercentage24h = marketData?.priceChangePercentage24h
)



fun List<CoinListResponse>.toCoinListEntity() = map {
    CoinListEntity(
        coinId = it.id,
        name = it.name
    )
}

fun List<CoinMarketEntity>.toCoinMarketsUI() = map {
    CoinMarkets(
        id = it.id,
        coinId = it.coinId,
        currentPrice = it.currentPrice,
        image = it.image,
        name = it.name,
        priceChangePercentage24h = it.priceChangePercentage24h
    )
}

fun List<CoinMarketResponse>.toCoinMarketsEntity() = map {
    CoinMarketEntity(
        coinId = it.id,
        ath = it.ath,
        athChangePercentage = it.athChangePercentage,
        athDate = it.athDate,
        atl = it.atl,
        atlChangePercentage = it.atlChangePercentage,
        atlDate = it.atlDate,
        circulatingSupply = it.circulatingSupply,
        currentPrice = it.currentPrice,
        fullyDilutedValuation = it.fullyDilutedValuation,
        high24h = it.high24h,
        image = it.image,
        lastUpdated = it.lastUpdated,
        low24h = it.low24h,
        marketCap = it.marketCap,
        marketCapChange24h = it.marketCapChange24h,
        marketCapChangePercentage24h = it.marketCapChangePercentage24h,
        marketCapRank = it.marketCapRank,
        maxSupply = it.maxSupply,
        name = it.name,
        priceChange24h = it.priceChange24h,
        priceChangePercentage24h = it.priceChangePercentage24h,
        symbol = it.symbol,
        totalSupply = it.totalSupply,
        totalVolume = it.totalVolume
    )
}

fun CoinDetail.toFavouriteUI() = CoinDetail(
    name = name ?: "",
    coinId = coinId ?: "",
    hashingAlgorithm = hashingAlgorithm ?: "",
    description = description ?: "",
    image = image ?: "",
    currentPrice = currentPrice ?: 0.0,
    priceChangePercentage24h = priceChangePercentage24h ?: 0.0
)

