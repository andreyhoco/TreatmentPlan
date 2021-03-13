package ru.andreyhoco.treatmentplan.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.persistence.database.TreatmentPlanDatabase
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepositoryImp
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake
import timber.log.Timber
import java.util.Calendar
import java.util.Calendar.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}