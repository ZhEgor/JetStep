package com.zhiro.jetstep.android.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import com.zhiro.jetstep.R

internal object NotificationsHelper {

    private const val NOTIFICATION_CHANNEL_ID = "general_notification_channel"

    fun createNotificationChannel(context: Context) {
        val notificationManager = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.fitness_notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    fun buildNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.fitness_notification_title))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }
}