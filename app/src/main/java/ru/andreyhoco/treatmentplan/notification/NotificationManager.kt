package ru.andreyhoco.treatmentplan.notification

import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import ru.andreyhoco.treatmentplan.App

class NotificationManager() {
    private val appContext = App.appContext
    private val notificationManager = NotificationManagerCompat.from(appContext)

    init {
        if (notificationManager.getNotificationChannel(CHANNEL_NEW_PROCEDURES) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannelCompat.Builder(CHANNEL_NEW_PROCEDURES, IMPORTANCE_DEFAULT)
                    .build()
            )
        }
    }



    companion object {
        private const val CHANNEL_NEW_PROCEDURES = "new procedures"

        private const val REQUEST_CONTENT = 1
    }

    object Singleton{
        val instance = NotificationManager()
    }
}