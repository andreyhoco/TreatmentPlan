package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup
import java.lang.System.currentTimeMillis
import java.util.concurrent.TimeUnit

class ParentWorkManager(appContext: Context, workerParameters: WorkerParameters,
                        repository: ProcedureAndPersonRepository) :
    CoroutineWorker(appContext, workerParameters) {
    private val workerParams = workerParameters
    private val workerRepository = repository

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        return@withContext try {
            loadData()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private suspend fun loadData() {
        val intakesProcedureTimeGroup =
                workerRepository.getProcedureGroupsBetweenDatesOneShot(1,1)
        intakesProcedureTimeGroup.forEach {
            instanceChildWorkManager(it)
        }
    }

    private fun instanceChildWorkManager(intakeProcedureTimeGroup: IntakeProcedureTimeGroup) {
        ShowNotificationWorkManager(applicationContext, workerParams, intakeProcedureTimeGroup)
        val workRequest = OneTimeWorkRequest
                .Builder(ShowNotificationWorkManager::class.java)
                .setInitialDelay(
                        getDelay(intakeProcedureTimeGroup),
                        TimeUnit.MILLISECONDS)
                .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
    private fun getDelay(intakeProcedureTimeGroup: IntakeProcedureTimeGroup): Long {
        return intakeProcedureTimeGroup.startTime - currentTimeMillis()
    }
}