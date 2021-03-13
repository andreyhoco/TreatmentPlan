package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkManager(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        return@withContext try {
            loadData()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private suspend fun loadData() {

    }

}