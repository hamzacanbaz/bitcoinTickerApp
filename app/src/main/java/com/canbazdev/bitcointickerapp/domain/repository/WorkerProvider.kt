package com.canbazdev.bitcointickerapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WorkerProvider {
    fun createWork()

    fun onSuccess(): LiveData<List<WorkInfo>>
}