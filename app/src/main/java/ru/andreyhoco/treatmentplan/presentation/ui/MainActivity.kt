package ru.andreyhoco.treatmentplan.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkManager.getInstance
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.notification.NotificationManager
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup
import ru.andreyhoco.treatmentplan.workmanager.ParentWorkManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO Тестовый метод.
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
//            createAndPushNotification()
            runWorkManager()

        }
    }

    //TODO Тестовый метод.
    private fun createAndPushNotification() {
        val notificationManger = NotificationManager(applicationContext)
        val notification = notificationManger.createNotification(IntakeProcedureTimeGroup())
        notificationManger.showNotification(notification)
    }

    //TODO Тестовый метод.
    private fun runWorkManager() {
        //TODO поменять на периодичный.
        val workRequest = OneTimeWorkRequest.Builder(
                ParentWorkManager::class.java
        )
                .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}