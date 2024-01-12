package com.canbazdev.bitcointickerapp.di

import com.canbazdev.bitcointickerapp.data.source.local.room.CoinsDAO
import com.canbazdev.bitcointickerapp.data.source.local.room.RoomDataSourceImpl
import com.canbazdev.bitcointickerapp.data.source.remote.BitcoinTickerService
import com.canbazdev.bitcointickerapp.data.source.remote.RemoteDataSourceImpl
import com.canbazdev.bitcointickerapp.domain.source.LocalDataSource
import com.canbazdev.bitcointickerapp.domain.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        coinService: BitcoinTickerService,
    ): RemoteDataSource =
        RemoteDataSourceImpl(coinService)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        coinsDAO: CoinsDAO
    ): LocalDataSource = RoomDataSourceImpl(coinsDAO)
}