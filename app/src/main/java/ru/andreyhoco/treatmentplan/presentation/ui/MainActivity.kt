package ru.andreyhoco.treatmentplan.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.persistence.Converters
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}