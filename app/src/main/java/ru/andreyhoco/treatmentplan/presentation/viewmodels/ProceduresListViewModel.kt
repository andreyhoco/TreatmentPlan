package ru.andreyhoco.treatmentplan.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.andreyhoco.treatmentplan.presentation.ui.ProcedureGroupItem
import ru.andreyhoco.treatmentplan.presentation.ui.ProcedureItem
import ru.andreyhoco.treatmentplan.presentation.ui.ProcedureListItem
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.ProcedureTimeGroup
import java.util.*

class ProceduresListViewModel : ViewModel() {
    lateinit var repository: ProcedureAndPersonRepository

    var proceduresList = MutableLiveData<List<ProcedureListItem>>(mutableListOf())

    fun loadProceduresByDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.MONTH)

        calendar.set(year, month, day, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val startOfCurrentDay = calendar.timeInMillis

        calendar.set(year, month, day, 23, 59, 59)
        calendar.set(Calendar.MILLISECOND, 999)

        val endOfCurrentDay = calendar.timeInMillis

        viewModelScope.launch {
            repository
                .getProcedureGroupsBetweenDates(startOfCurrentDay, endOfCurrentDay)
                .collect { listOfProcedureTimeGroup ->
                    onProceduresLoaded(listOfProcedureTimeGroup)
                }
        }
    }

    private fun onProceduresLoaded(proceduresByGroup: List<ProcedureTimeGroup>) {
        val proceduresItemsList: MutableList<ProcedureListItem> = mutableListOf()

        proceduresByGroup.forEach { procedureTimeGroup ->
            val proceduresItemsInGroup: MutableList<ProcedureItem> = mutableListOf()

            procedureTimeGroup.procedures.forEach { procedure ->
                procedure.timesOfIntake
                    .filter { timeOfIntake ->
                        timeOfIntake.timeOfTakes in procedureTimeGroup.startTime..procedureTimeGroup.endTime
                    }
                    .forEach { timeOfIntake ->
                        proceduresItemsInGroup.add(ProcedureItem(procedure, timeOfIntake))
                    }
            }

            if (proceduresItemsInGroup.size > 0) {
                proceduresItemsList.add(ProcedureGroupItem(procedureTimeGroup))
                proceduresItemsList.addAll(proceduresItemsInGroup)
            }
        }

        proceduresList.value = proceduresItemsList
    }
}