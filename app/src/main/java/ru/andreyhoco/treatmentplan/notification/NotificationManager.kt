package ru.andreyhoco.treatmentplan.notification

import android.app.Notification
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import androidx.core.app.Person
import ru.andreyhoco.treatmentplan.App
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.ProcedureTimeGroup
import java.text.SimpleDateFormat
import java.util.*
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

    fun createNotification(persons: List<Person>, procedureTimeGroup: ProcedureTimeGroup)
            : Notification {
        return NotificationCompat.Builder(appContext, CHANNEL_NEW_PROCEDURES)
            .setContentTitle(
                    createTitleNotification(procedureTimeGroup)
            )
            .setContentText(
                    createContentTextNotification(persons, procedureTimeGroup)
            )
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
    }

    fun showNotification(notification: Notification) {
        notificationManager.notify("", 1, notification)
    }

    private fun createTitleNotification(procedureTimeGroup: ProcedureTimeGroup) : String {
        val title = StringBuilder()

        title
                .append(appContext.getString(R.string.V))
                .append(
                        getTimesOfTakingProcedure(procedureTimeGroup)
                )
                .append(appContext.getString(R.string.dash))
                .append(procedureTimeGroup.procedures.size)
                .append(appContext.getString(R.string.procedure))
        //TODO разобраться со склонениями слова "процедура"

        return title.toString()
    }

    private fun getTimesOfTakingProcedure(procedureTimeGroup: ProcedureTimeGroup) : String {
        val timeOfTaking = StringBuilder()

        val formatter = SimpleDateFormat("HH:MM", Locale.ENGLISH)

        if (procedureTimeGroup.procedures.isNotEmpty()) {
            timeOfTaking.append(formatter.parse(procedureTimeGroup.procedures[0].toString()))
        }
        return timeOfTaking.toString()
    }

    private fun createContentTextNotification(persons: List<Person>,
                                              procedureTimeGroup: ProcedureTimeGroup) : String {
        val contentText = StringBuilder()

        contentText
                .append("Даше")
                .append(appContext.getString(R.string.colon))
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