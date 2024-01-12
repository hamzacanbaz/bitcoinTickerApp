package com.canbazdev.bitcointickerapp.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.canbazdev.bitcointickerapp.common.Constants
import com.canbazdev.bitcointickerapp.common.Constants.SYNC_DATA
import com.canbazdev.bitcointickerapp.common.extensions.showDLog
import com.canbazdev.bitcointickerapp.domain.repository.WorkerProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerProviderImpl @Inject constructor(context: Context) : WorkerProvider {

    private val workManager = WorkManager.getInstance(context)
    override fun createWork() {
        showDLog("worker created")
        val workRequest = PeriodicWorkRequestBuilder<CoinWorker>(
            15, TimeUnit.MINUTES, 15, TimeUnit.MINUTES
        ).addTag(SYNC_DATA).build()

        workManager.enqueueUniquePeriodicWork(
            Constants.SYNC_DATA_WORK_NAME, ExistingPeriodicWorkPolicy.REPLACE, workRequest
        )
    }

    override fun onSuccess() = workManager.getWorkInfosByTagLiveData(SYNC_DATA)
}