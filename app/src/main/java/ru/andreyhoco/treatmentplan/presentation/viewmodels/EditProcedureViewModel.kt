package ru.andreyhoco.treatmentplan.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake

class EditProcedureViewModel(
        private val repository: ProcedureAndPersonRepository
): ViewModel() {
    var procedure = MutableLiveData<Procedure?>(null)
    var persons = MutableLiveData<List<Person>>(mutableListOf())

    fun addPerson(personName: String) {
        viewModelScope.launch {
            repository.insertPerson(Person(0, personName, 0))
        }
    }

    fun loadPersons() {
        viewModelScope.launch {
            repository.getAllPerson().collect { listOfPerson ->
                persons.value = listOfPerson
            }
        }
    }

    fun addTimeOfIntake(time: Long, procedure: Procedure?) {
        procedure?.let { proc ->
            val sameTimeList = procedure.timesOfIntake
                .map { timeOfIntake:TimeOfIntake -> timeOfIntake.timeOfTakes }
                .filter { timeOfTakes: Long -> timeOfTakes == time }

            Log.d("TApp", "list size: ${sameTimeList.size}")

            if (sameTimeList.isEmpty()) {
                val newList: MutableList<TimeOfIntake> = mutableListOf()

                Log.d("TApp", "new list size: ${newList.size}")
                newList.addAll(procedure.timesOfIntake)
                Log.d("TApp", "new list size: ${newList.size}")
                newList.add(TimeOfIntake(time, false))
                Log.d("TApp", "new list size: ${newList.size}")
                newList.sortBy { timeOfIntake ->
                    timeOfIntake.timeOfTakes
                }
                Log.d("TApp", "new list size: ${newList.size}")

                procedure.timesOfIntake = newList.toList()
            }
        }
    }

    fun deleteTimeOfIntake(timeOfIntake: TimeOfIntake, procedure: Procedure?) {
        procedure?.let { proc ->
            val newList: MutableList<TimeOfIntake> = mutableListOf()

            newList.addAll(proc.timesOfIntake)
            newList.remove(timeOfIntake)

            proc.timesOfIntake = newList
        }
    }

    fun saveProcedure(procedure: Procedure?) {
        procedure?.let { proc ->
            viewModelScope.launch {
                repository.deleteProceduresByIds(proc.id)
                repository.insertProcedure(proc)
            }
        }
    }
}