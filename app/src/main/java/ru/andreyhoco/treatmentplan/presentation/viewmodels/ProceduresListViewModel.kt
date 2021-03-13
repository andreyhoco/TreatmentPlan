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
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake
import java.util.*

class ProceduresListViewModel : ViewModel() {
    lateinit var repository: ProcedureAndPersonRepository

    var proceduresList = MutableLiveData<List<ProcedureListItem>>(mutableListOf())
    var selectedProcedure = MutableLiveData<Procedure?>(null)

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
                .collect { listOfIntakeProcedureTimeGroup ->
                    onProceduresLoaded(listOfIntakeProcedureTimeGroup)
                }
        }
    }

    private fun onProceduresLoaded(listOfIntakeProcedureTimeGroup: List<IntakeProcedureTimeGroup>) {
        val proceduresItemsList: MutableList<ProcedureListItem> = mutableListOf()

        listOfIntakeProcedureTimeGroup.forEach { intakeProcedureTimeGroup ->
            val proceduresItemsInGroup: MutableList<ProcedureItem> = mutableListOf()

            intakeProcedureTimeGroup.procedures.forEach { intakeProcedure ->
                proceduresItemsInGroup.add(ProcedureItem(intakeProcedure))
            }

            if (proceduresItemsInGroup.size > 0) {
                proceduresItemsList.add(ProcedureGroupItem(intakeProcedureTimeGroup))
                proceduresItemsList.addAll(proceduresItemsInGroup)
            }
        }

        proceduresList.value = proceduresItemsList
    }

    fun getPersonById(procedureId: Long) {
        viewModelScope.launch {
            repository
                .getProcedureById(procedureId)
                .collect { procedure ->
                    selectedProcedure.value = procedure
                }
        }
    }

    fun setCheckBox(procedureId: Long, timeOfIntake: TimeOfIntake) {
        // TODO: call update done flag in repository
    }
}