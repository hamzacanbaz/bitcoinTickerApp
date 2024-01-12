package com.canbazdev.bitcointickerapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.canbazdev.bitcointickerapp.common.Constants.CHANNEL_ID
import com.canbazdev.bitcointickerapp.common.Constants.CHANNEL_NAME
import com.canbazdev.bitcointickerapp.R
import com.canbazdev.bitcointickerapp.common.extensions.showDLog
import kotlin.random.Random

object NotificationUtils {

    fun showNotification(context: Context, title: String, description: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val builder = createNotificationCompat(context, title, description)

        notificationManager.notify(Random.nextInt(), builder.build())
        showDLog("notification sent")

    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "Your coin list refreshed"
            }
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun createNotificationCompat(
        context: Context,
        title: String,
        description: String
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }
}