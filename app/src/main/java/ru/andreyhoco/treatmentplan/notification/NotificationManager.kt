package ru.andreyhoco.treatmentplan.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.StringBuilder

class NotificationManager(context: Context) {
//TODO нужно пробросить контекст.
//        private val appContext = App.appContext
    private val appContext = context
    private val notificationManager = NotificationManagerCompat.from(appContext)

    init {
        if (notificationManager.getNotificationChannel(CHANNEL_NEW_PROCEDURES) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannelCompat.Builder(CHANNEL_NEW_PROCEDURES, IMPORTANCE_DEFAULT)
                    .setName(appContext.getString(R.string.channel_new_procedure))
                    .setDescription(appContext.getString(R.string.channel_new_procedure_description))
                    .build()
            )
        }
    }

    fun createNotification(procedureTimeGroup: IntakeProcedureTimeGroup)
            : Notification {
        return NotificationCompat.Builder(appContext, CHANNEL_NEW_PROCEDURES)
            //TODO нужна иконка для Уведомления, иначе падает.
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(
                    createTitleNotification(procedureTimeGroup)
            )
            .setContentText(
                    createContentTextNotification(procedureTimeGroup)
            )
            .setStyle(NotificationCompat.BigTextStyle())
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    fun showNotification(notification: Notification) {
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createTitleNotification(procedureTimeGroup: IntakeProcedureTimeGroup) : String {
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

    private fun getTimesOfTakingProcedure(procedureTimeGroup: IntakeProcedureTimeGroup) : String {
        val timeOfTaking = StringBuilder()

        val formatter = SimpleDateFormat("HH:MM", Locale.ENGLISH)

        if (procedureTimeGroup.procedures.isNotEmpty()) {
            val date = Date(procedureTimeGroup.procedures[0].timeOfIntake.timeOfTakes)
            timeOfTaking.append(formatter.format(date))
        }
        return timeOfTaking.toString()
    }

    private fun createContentTextNotification(procedureTimeGroup: IntakeProcedureTimeGroup) : String {
        val contentText = StringBuilder()

        procedureTimeGroup.procedures.forEach {
            contentText
                    .append(it.person.name)
                    .append(appContext.getString(R.string.colon))
                    .append(it.title)
                    .append(System.getProperty("line.separator"))
        }

        return contentText.toString().dropLast(1)
    }

    companion object {
        private const val CHANNEL_NEW_PROCEDURES = "new procedures"
        private const val NOTIFICATION_ID = 100
    }

//    object Singleton{
//        val instance = NotificationManager()
//    }
}