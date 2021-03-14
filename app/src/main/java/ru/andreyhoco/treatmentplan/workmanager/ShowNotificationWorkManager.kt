package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.andreyhoco.treatmentplan.notification.NotificationManager
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup

class ShowNotificationWorkManager(appContext: Context, workerParams: WorkerParameters) :
        CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {

            val data = inputData.getString("data")
            val intakeProcedureTimeGroup =
                    data?.let { Json.decodeFromString<IntakeProcedureTimeGroup>(it) }


            intakeProcedureTimeGroup?.let { showNotification(it) }
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private fun showNotification(intakeProcedureTimeGroup: IntakeProcedureTimeGroup) {

        val notificationManger = NotificationManager(applicationContext)
        val notification = notificationManger.createNotification(intakeProcedureTimeGroup)
        notificationManger.showNotification(notification)
    }
}