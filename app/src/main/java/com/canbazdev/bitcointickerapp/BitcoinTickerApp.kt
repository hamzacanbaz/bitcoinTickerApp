package com.canbazdev.bitcointickerapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.canbazdev.bitcointicker.data.local.sharedPreferences.MyPreferences
import com.canbazdev.bitcointickerapp.data.worker.CoinWorker
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BitcoinTickerApp : Application(), Configuration.Provider {

    private var sharedPref: MyPreferences? = null
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
        sharedPref = MyPreferences(applicationContext).getPreferences()
//        setWork()
    }

    companion object {
        lateinit var instance: BitcoinTickerApp
            private set
    }

//    private fun setWork() {
//        println("set work")
//        val workRequest = PeriodicWorkRequest.Builder(
//            CoinWorker::class.java,
//            15,
//            TimeUnit.MINUTES
//        ).build()
//
//        WorkManager.getInstance(this).enqueue(workRequest)
//
//    }


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}

