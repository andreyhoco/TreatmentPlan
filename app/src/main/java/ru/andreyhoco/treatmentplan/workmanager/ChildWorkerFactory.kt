package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class ChildWorkerFactory () : WorkerFactory() {
    override fun createWorker(appContext: Context,
                              workerClassName: String,
                              workerParameters: WorkerParameters)
            : ListenableWorker? {
        return when(workerClassName) {
            ChildWorkManager::class.java.name ->
                ChildWorkManager(appContext, workerParameters)
            else ->
                null
        }
    }
}