package com.canbazdev.bitcointickerapp.di

import com.canbazdev.bitcointickerapp.common.Constants.BASE_URL
import com.canbazdev.bitcointickerapp.data.source.remote.BitcoinTickerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideBitcoinTickerService(): BitcoinTickerService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BitcoinTickerService::class.java)
}