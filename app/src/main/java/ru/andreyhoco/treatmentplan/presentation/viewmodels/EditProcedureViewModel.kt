package ru.andreyhoco.treatmentplan.presentation.viewmodels

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

class EditProcedureViewModel : ViewModel() {
    private lateinit var repository: ProcedureAndPersonRepository

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
            val sameTimeList = proc.timesOfIntake
                .map { timeOfIntake -> timeOfIntake.timeOfTakes }
                .filter { timeOfTakes -> timeOfTakes == time }

            if (sameTimeList.isEmpty()) {
                val newList: MutableList<TimeOfIntake> = mutableListOf()

                newList.addAll(proc.timesOfIntake)
                newList.add(TimeOfIntake(time, false))
                newList.sortBy { timeOfIntake ->
                    timeOfIntake.timeOfTakes
                }

                proc.timesOfIntake = newList
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
}