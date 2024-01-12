package com.canbazdev.bitcointickerapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.canbazdev.bitcointickerapp.data.model.coinList.CoinListEntity
import com.canbazdev.bitcointickerapp.data.model.coinMarket.CoinMarketEntity

@Database(
    entities = [CoinListEntity::class, CoinMarketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CoinsRoomDB : RoomDatabase() {
    abstract fun coinsDAO(): CoinsDAO
}