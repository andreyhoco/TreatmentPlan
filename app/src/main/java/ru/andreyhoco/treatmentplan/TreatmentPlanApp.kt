package ru.andreyhoco.treatmentplan

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import ru.andreyhoco.treatmentplan.workmanager.ParentWorkerFactory

class TreatmentPlanApp : Application(), Configuration.Provider {
    val appDi: AppDi by lazy { AppDi(applicationContext) }

    override fun getWorkManagerConfiguration(): Configuration {
        val myWorkerFactory = DelegatingWorkerFactory()
        myWorkerFactory.addFactory(ParentWorkerFactory(
                appDi.procedureAndPersonRepository
        ))

        return Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .setWorkerFactory(myWorkerFactory)
                .build()
    }
}