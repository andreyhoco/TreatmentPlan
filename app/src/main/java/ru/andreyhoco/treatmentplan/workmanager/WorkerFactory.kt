package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository

class WorkerFactory (private val repository: ProcedureAndPersonRepository) : WorkerFactory() {
    override fun createWorker(appContext: Context,
                              workerClassName: String,
                              workerParameters: WorkerParameters)
            : ListenableWorker? {
        return when(workerClassName) {
            ParentWorkManager::class.java.name ->
                ParentWorkManager(appContext, workerParameters, repository)
            else ->
                null
        }
    }
}