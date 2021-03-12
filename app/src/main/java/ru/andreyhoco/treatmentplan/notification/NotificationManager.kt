package ru.andreyhoco.treatmentplan.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import androidx.annotation.MainThread
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import androidx.core.app.Person
import ru.andreyhoco.treatmentplan.App
import ru.andreyhoco.treatmentplan.presentation.ui.MainActivity
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure

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

    fun createNotification(persons: List<Person>, procedure: List<Procedure>) : Notification {
        return NotificationCompat.Builder(appContext, CHANNEL_NEW_PROCEDURES)
            .setContentTitle("")
            .setContentText("")
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(
                PendingIntent.getActivity(
                    appContext,
                    REQUEST_CONTENT,
                    Intent(appContext, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setAutoCancel(true)
            .build()
    }

    fun showNotification(notification: Notification) {
        notificationManager.notify("", 1, notification)
    }

    companion object {
        private const val CHANNEL_NEW_PROCEDURES = "new procedures"

        private const val REQUEST_CONTENT = 1
    }

    object Singleton{
        val instance = NotificationManager()
    }
}