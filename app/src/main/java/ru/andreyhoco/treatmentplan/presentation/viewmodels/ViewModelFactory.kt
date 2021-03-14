package ru.andreyhoco.treatmentplan.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val applicationContext: Context
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            ProceduresListViewModel::class.java ->
                ProceduresListViewModel()
            EditProcedureViewModel::class.java ->
                EditProcedureViewModel()
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}