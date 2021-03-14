package ru.andreyhoco.treatmentplan

import android.content.Context
import ru.andreyhoco.treatmentplan.persistence.database.TreatmentPlanDatabase
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepositoryImp

class AppDi(private val applicationContext: Context) {
    private val treatmentPlanAppDb = TreatmentPlanDatabase.create(applicationContext)
    //Add notifications

    val procedureAndPersonRepository = ProcedureAndPersonRepositoryImp(treatmentPlanAppDb)
}