package ru.andreyhoco.treatmentplan.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake
import java.util.*

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

            if (sameTimeList.isEmpty()) {
                val newList: MutableList<TimeOfIntake> = mutableListOf()

                newList.addAll(procedure.timesOfIntake)
                newList.add(TimeOfIntake(time, false))
                newList.sortBy { timeOfIntake ->
                    timeOfIntake.timeOfTakes
                }

                procedure.timesOfIntake = newList.toList()
            }
        }
    }

    fun deleteTimeOfIntake(timeOfIntake: TimeOfIntake, procedure: Procedure?) {
        procedure?.let { proc ->
            val newList: MutableList<TimeOfIntake> = mutableListOf()

            newList.addAll(procedure.timesOfIntake)
            newList.remove(timeOfIntake)

            procedure.timesOfIntake = newList.toList()
        }
    }

    fun saveProcedure(procedure: Procedure?) {
        procedure?.let { proc ->
            var time = proc.startDate
            val newListOfIntakes = mutableListOf<TimeOfIntake>()
            while (time < proc.endDate) {
                proc.timesOfIntake.forEach { timeOfIntake ->
                    newListOfIntakes.add(TimeOfIntake(time + timeOfIntake.timeOfTakes, false))
                }
                time += 86400000
            }

            proc.timesOfIntake = newListOfIntakes
            viewModelScope.launch {
                val calendar = Calendar.getInstance()
                proc.timesOfIntake.forEach {
                    calendar.timeInMillis = it.timeOfTakes
                    val intakeDate = calendar.time
                    Log.d("SAVE","$intakeDate")
                }

                repository.insertProcedure(proc)
            }
        }
    }
}