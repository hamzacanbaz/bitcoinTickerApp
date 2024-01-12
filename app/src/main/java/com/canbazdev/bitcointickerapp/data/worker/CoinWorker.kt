package com.canbazdev.bitcointickerapp.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.canbazdev.bitcointickerapp.common.Constants.DESCRIPTION
import com.canbazdev.bitcointickerapp.common.Constants.TITLE
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.common.extensions.showDLog
import com.canbazdev.bitcointickerapp.domain.repository.FirebaseRepository
import com.canbazdev.bitcointickerapp.domain.source.RemoteDataSource
import com.canbazdev.bitcointickerapp.utils.NotificationUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CoinWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val authRepository: FirebaseRepository,
    private val remoteDataSource: RemoteDataSource
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        showDLog("do work method executed")
        return try {

            var equalState = true

            val coinsMarkets = remoteDataSource.getCoinMarkets()

            authRepository.getFavourites().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        showDLog("worker executed and coin list refreshed")
                        if (result.data.isNotEmpty()) {
                            result.data.forEach { favourite ->
                                coinsMarkets.forEach { coinMarkets ->
                                    if (favourite.name == coinMarkets.name && favourite.currentPrice != coinMarkets.currentPrice) {
                                        equalState = !equalState
                                    }
                                }
                            }

                            if (equalState) NotificationUtils.showNotification(
                                applicationContext,
                                TITLE,
                                DESCRIPTION
                            )
                        }
                    }
                    else -> {}
                }
            }

            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }

    }


}