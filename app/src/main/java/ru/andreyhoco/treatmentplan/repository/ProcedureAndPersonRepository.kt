package ru.andreyhoco.treatmentplan.repository

import kotlinx.coroutines.flow.Flow
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.ProcedureTimeGroup

interface ProcedureAndPersonRepository {
    fun getProcedureById(id: Int) : Flow<Procedure>

    fun getProcedureGroupsByDate(date: Long) : Flow<List<ProcedureTimeGroup>>

    fun getAllProcedures() : Flow<List<Procedure>>

    fun getPersonById(id: Long) : Flow<Person>

    fun getAllPerson() : Flow<List<Person>>

    fun insertProcedure(procedure: Procedure)

    fun insertPerson(person: Person)
}