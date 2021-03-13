package ru.andreyhoco.treatmentplan.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.notification.NotificationManager
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO Тестовый метод.
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
//            createAndPushNotification()
        }
    }

    //TODO Тестовый метод.
    private fun createAndPushNotification() {
        val notificationManger = NotificationManager(applicationContext)
        val notification = notificationManger.createNotification(IntakeProcedureTimeGroup())
        notificationManger.showNotification(notification)
    }
}