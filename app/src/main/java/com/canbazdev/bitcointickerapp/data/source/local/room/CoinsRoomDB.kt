package com.canbazdev.bitcointickerapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.canbazdev.bitcointickerapp.data.model.coinlist.CoinListEntity
import com.canbazdev.bitcointickerapp.data.model.coinmarket.CoinMarketEntity
import com.canbazdev.bitcointickerapp.domain.model.CoinList
import com.canbazdev.bitcointickerapp.domain.model.CoinMarkets

@Database(
    entities = [CoinListEntity::class, CoinMarketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CoinsRoomDB : RoomDatabase() {
    abstract fun coinsDAO(): CoinsDAO
}