package ru.andreyhoco.treatmentplan.notification

import android.app.Notification
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import androidx.core.app.Person
import ru.andreyhoco.treatmentplan.App
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import kotlin.text.StringBuilder

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

    fun createNotification(persons: List<Person>, procedures: List<Procedure>) : Notification {
        return NotificationCompat.Builder(appContext, CHANNEL_NEW_PROCEDURES)
            .setContentTitle(
                    createTitleNotification(procedures)
            )
            .setContentText(
                    createContentTextNotification(persons, procedures)
            )
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
    }

    fun showNotification(notification: Notification) {
        notificationManager.notify("", 1, notification)
    }

    private fun createTitleNotification(procedures: List<Procedure>) : String {
        val title = StringBuilder()

        title
                .append("B")
                .append("10:05")
                .append(" - ")
                .append("4")
                .append("процедуры")

        return title.toString()
    }

    private fun createContentTextNotification(persons: List<Person>, procedures: List<Procedure>) : String {
        val contentText = StringBuilder()

        contentText
                .append("Даше")
                .append(" : ")
                .append("полоскание горла")

        return contentText.toString()
    }

    companion object {
        private const val CHANNEL_NEW_PROCEDURES = "new procedures"
    }

    object Singleton{
        val instance = NotificationManager()
    }
}