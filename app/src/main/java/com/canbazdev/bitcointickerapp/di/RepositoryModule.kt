package com.canbazdev.bitcointickerapp.di

import com.canbazdev.bitcointickerapp.data.source.local.room.CoinsRoomDB
import com.canbazdev.bitcointickerapp.domain.source.LocalDataSource
import com.canbazdev.bitcointickerapp.data.repository.CoinsRepositoryImpl
import com.canbazdev.bitcointickerapp.domain.repository.CoinsRepository
import com.canbazdev.bitcointickerapp.domain.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        coinsRoomDB: CoinsRoomDB,
        @Named("Default") coroutineContextDefault: CoroutineDispatcher
    ): CoinsRepository =
        CoinsRepositoryImpl(remoteDataSource, localDataSource, coinsRoomDB, coroutineContextDefault)

    @Provides
    @Singleton
    @Named("IO")
    fun provideCoContextIO(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Singleton
    @Named("Default")
    fun provideCoContextDefault(): CoroutineDispatcher = Dispatchers.Default

}