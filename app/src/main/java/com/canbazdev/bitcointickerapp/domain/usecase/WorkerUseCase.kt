package com.canbazdev.bitcointickerapp.domain.usecase

import com.canbazdev.bitcointickerapp.domain.repository.WorkerProvider
import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val workerProvider: WorkerProvider
) {
    operator fun invoke() = workerProvider.onSuccess()

}