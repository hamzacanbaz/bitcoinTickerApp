package com.canbazdev.bitcointickerapp.domain.usecase

import javax.inject.Inject

class WorkerUseCase @Inject constructor(
    private val workerProvider: WorkerProvider
) {

    init {
        println("workerusecase ")
    }
    operator fun invoke() = workerProvider.onSuccess()

}