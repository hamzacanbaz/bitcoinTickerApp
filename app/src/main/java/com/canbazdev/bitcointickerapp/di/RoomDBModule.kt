package com.canbazdev.bitcointickerapp.di

import android.content.Context
import androidx.room.Room
import com.canbazdev.bitcointickerapp.common.Constants.ROOM_DB_NAME
import com.canbazdev.bitcointickerapp.data.source.local.room.CoinsDAO
import com.canbazdev.bitcointickerapp.data.source.local.room.CoinsRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Provides
    @Singleton
    fun provideFavoritesRoomDB(@ApplicationContext appContext: Context): CoinsRoomDB =
        Room.databaseBuilder(
            appContext,
            CoinsRoomDB::class.java,
            ROOM_DB_NAME
        ).build()

    @Provides
    @Singleton
    fun provideCoinsDAO(coinsRoomDB: CoinsRoomDB): CoinsDAO =
        coinsRoomDB.coinsDAO()
}