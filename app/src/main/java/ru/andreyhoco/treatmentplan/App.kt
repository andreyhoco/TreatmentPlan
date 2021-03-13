package ru.andreyhoco.treatmentplan

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import ru.andreyhoco.treatmentplan.workmanager.ChildWorkerFactory
import ru.andreyhoco.treatmentplan.workmanager.ParentWorkerFactory

class App : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val myWorkerFactory = DelegatingWorkerFactory()
        myWorkerFactory.addFactory(ParentWorkerFactory(
                //TODO нужен репозиторий.
        ))
        myWorkerFactory.addFactory(ChildWorkerFactory())

        return Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .setWorkerFactory(myWorkerFactory)
                .build()
    }

    companion object {
        lateinit var appContext: Context
    }
}