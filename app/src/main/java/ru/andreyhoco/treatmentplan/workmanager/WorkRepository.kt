package ru.andreyhoco.treatmentplan.workmanager

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest

import java.util.concurrent.TimeUnit

class WorkRepository {
    private val updateConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresCharging(true)
        .build()

    val periodicStartNotificationWorker = PeriodicWorkRequest.Builder(
        ParentWorkManager::class.java,
        6L,
        TimeUnit.HOURS
    )
        .setConstraints(updateConstraints)
        .addTag(UNIQUE_UPDATE_TAG)
        .build()

    companion object {
        const val UNIQUE_UPDATE_TAG = "update_tag"
        const val PERIODIC_PROCEDURE_NOTIFICATION = "procedure_notification"
    }
}