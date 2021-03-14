package ru.andreyhoco.treatmentplan.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository

class ViewModelFactory(
    private val repository: ProcedureAndPersonRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            ProceduresListViewModel::class.java ->
                ProceduresListViewModel(repository)
            EditProcedureViewModel::class.java ->
                EditProcedureViewModel(repository)
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}