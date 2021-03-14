package ru.andreyhoco.treatmentplan

import android.app.Application

class TreatmentPlanApp : Application() {
    val appDi: AppDi by lazy { AppDi(applicationContext) }
}