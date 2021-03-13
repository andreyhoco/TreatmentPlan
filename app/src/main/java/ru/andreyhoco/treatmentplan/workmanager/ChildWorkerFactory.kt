package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup

class ChildWorkerFactory (private val intakeProcedureTimeGroup: IntakeProcedureTimeGroup) : WorkerFactory() {
    override fun createWorker(appContext: Context,
                              workerClassName: String,
                              workerParameters: WorkerParameters)
            : ListenableWorker? {
        return when(workerClassName) {
            ShowNotificationWorkManager::class.java.name ->
                ShowNotificationWorkManager(appContext, workerParameters, intakeProcedureTimeGroup)
            else ->
                null
        }
    }
}