package ru.andreyhoco.treatmentplan.workmanager

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup
import timber.log.Timber
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.TimeUnit

class ParentWorkManager(private val appContext: Context, private val workerParams: WorkerParameters,
                        repository: ProcedureAndPersonRepository) :
    CoroutineWorker(appContext, workerParams) {
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
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(year, month, day, 0, 0, 1)
        val startOfCurrentDay = calendar.timeInMillis
        calendar.set(year, month, day, 23, 59, 59)
        val endOfCurrentDay = calendar.timeInMillis

        val intakesProcedureTimeGroup =
                workerRepository.getProcedureGroupsBetweenDatesOneShot(startOfCurrentDay,endOfCurrentDay)
        intakesProcedureTimeGroup.forEach {
            instanceShowNotificationWorkManager(it)
        }
    }

    private fun instanceShowNotificationWorkManager(intakeProcedureTimeGroup: IntakeProcedureTimeGroup) {
        ShowNotificationWorkManager(applicationContext, workerParams)

        val data = Data.Builder()
        data.putString("data", Json.encodeToString(intakeProcedureTimeGroup))


        val workRequest = OneTimeWorkRequest
                .Builder(ShowNotificationWorkManager::class.java)
                .setInputData(data.build())
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