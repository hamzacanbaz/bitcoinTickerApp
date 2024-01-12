package com.canbazdev.bitcointickerapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.canbazdev.bitcointickerapp.data.source.local.sharedPreferences.MyPreferences
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
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
    }

    companion object {
        lateinit var instance: BitcoinTickerApp
            private set
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}

