package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.andreyhoco.treatmentplan.notification.NotificationManager
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup

class ShowNotificationWorkManager (appContext: Context, workerParams: WorkerParameters,
                                   data: IntakeProcedureTimeGroup) :
        CoroutineWorker(appContext, workerParams) {
    private val intakeProcedureTimeGroup = data

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            showNotification()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private fun showNotification() {
        val notificationManger = NotificationManager(applicationContext)
        val notification = notificationManger.createNotification(intakeProcedureTimeGroup)
        notificationManger.showNotification(notification)
    }
}