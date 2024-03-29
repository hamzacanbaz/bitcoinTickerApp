package com.canbazdev.bitcointickerapp.data.model.coinList

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_list")
data class CoinListEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "coinId")
    val coinId: String?,

    @ColumnInfo(name = "name")
    val name: String?
)