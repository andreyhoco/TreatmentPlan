package ru.andreyhoco.treatmentplan.repository

import kotlinx.coroutines.flow.Flow
import ru.andreyhoco.treatmentplan.repository.modelEntities.IntakeProcedureTimeGroup
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure

class FakeRepository : ProcedureAndPersonRepository {
    override suspend fun getProcedureById(id: Long): Flow<Procedure> {
        TODO("Not yet implemented")
    }

    override suspend fun getProcedureGroupsBetweenDates(firstDate: Long, secondDate: Long): Flow<List<IntakeProcedureTimeGroup>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProcedureGroupsBetweenDatesOneShot(firstDate: Long, secondDate: Long): List<IntakeProcedureTimeGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProcedures(): Flow<List<Procedure>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPersonById(id: Long): Flow<Person> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPerson(): Flow<List<Person>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProcedure(procedure: Procedure) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPerson(person: Person) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePersonsByIds(ids: List<Long>) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePersonsByIds(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPersons() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProceduresByIds(ids: List<Long>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProceduresByIds(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllProcedures() {
        TODO("Not yet implemented")
    }
}