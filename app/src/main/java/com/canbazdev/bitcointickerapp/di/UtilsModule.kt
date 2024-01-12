package com.canbazdev.bitcointickerapp.di

import android.app.Application
import com.canbazdev.bitcointickerapp.data.worker.WorkerImpl
import com.canbazdev.bitcointickerapp.domain.usecase.WorkerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {


//    @Provides
//    @Singleton
//    fun provideStringResourceProvider(application: Application): StringResourceProvider {
//        return StringResourceProviderImpl(application)
//    }
//
    @Provides
    @Singleton
    fun provideWorkerProvider(application: Application): WorkerProvider {
        return WorkerImpl(application)
    }
}