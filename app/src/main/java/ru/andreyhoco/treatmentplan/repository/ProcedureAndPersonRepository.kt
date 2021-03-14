package ru.andreyhoco.treatmentplan.repository

import kotlinx.coroutines.flow.Flow
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup

interface ProcedureAndPersonRepository {
    fun getProcedureById(id: Long) : Flow<Procedure>

    fun getProcedureGroupsBetweenDates(firstDate: Long, secondDate: Long) : Flow<List<IntakeProcedureTimeGroup>>

    suspend fun getProcedureGroupsBetweenDatesOneShot(firstDate: Long, secondDate: Long) : List<IntakeProcedureTimeGroup>

    fun getAllProcedures(): Flow<List<Procedure>>

    fun getPersonById(id: Long): Flow<Person>

    fun getAllPerson() : Flow<List<Person>>

    suspend fun insertProcedure(procedure: Procedure)

    suspend fun insertPerson(person: Person)

    suspend fun deletePersonsByIds(ids: List<Long>)

    suspend fun deletePersonsByIds(id: Long)

    suspend fun deleteAllPersons()

    suspend fun deleteProceduresByIds(ids: List<Long>)

    suspend fun deleteProceduresByIds(id: Long)

    suspend fun deleteAllProcedures()
}