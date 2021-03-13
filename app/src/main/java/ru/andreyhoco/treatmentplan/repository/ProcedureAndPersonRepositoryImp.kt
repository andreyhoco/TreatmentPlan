package ru.andreyhoco.treatmentplan.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.andreyhoco.treatmentplan.persistence.database.TreatmentPlanDatabase
import ru.andreyhoco.treatmentplan.persistence.entities.PersonEntity
import ru.andreyhoco.treatmentplan.persistence.entities.ProcedureEntity
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.ProcedureTimeGroup
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake

class ProcedureAndPersonRepositoryImp(
        private val appDatabase: TreatmentPlanDatabase
) : ProcedureAndPersonRepository {
    private val personDao = appDatabase.personDao
    private val procedureDao = appDatabase.procedureDao

    override suspend fun getAllPerson(): Flow<List<Person>> {
        return personDao.getAllPersons().map { entityList ->
            entityList.map { personEntity ->
                personEntity.toPerson()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPersonById(id: Long): Flow<Person> {
        return personDao.getPersonById(id).map { personEntity ->
                personEntity.toPerson()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertPerson(person: Person) {
        personDao.insert(person.toPersonEntity())
    }

    override suspend fun getAllProcedures(): Flow<List<Procedure>> {
        return procedureDao.getAllProcedures().map { procedureList ->
            procedureList.map { procedureEntity ->
                val personEntity = personDao.getOneShotPersonById(procedureEntity.personId)
                procedureEntity.toProcedure(personEntity.toPerson())
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getProcedureById(id: Long): Flow<Procedure> {
        return procedureDao.getProcedureById(id).map { procedureEntity ->
            val personEntity = personDao.getOneShotPersonById(procedureEntity.id)
            procedureEntity.toProcedure(personEntity.toPerson())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getProcedureGroupsBetweenDates(
        firstDate: Long,
        secondDate: Long
    ): Flow<List<ProcedureTimeGroup>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProcedure(procedure: Procedure) {
        procedureDao.insert(procedure.toProcedureEntity(procedure.person))

        TODO("Добавить пуск воркера с нотификацией")
    }

    override suspend fun deleteAllPersons() {
        personDao.deleteAllPersons()
    }

    override suspend fun deleteAllProcedures() {
        procedureDao.deleteAllProcedures()
    }

    override suspend fun deletePersonsByIds(ids: List<Long>) {
        personDao.deletePersonsByIds(ids)
    }

    override suspend fun deletePersonsByIds(id: Long) {
        personDao.deletePersonsByIds(listOf(id))
    }

    override suspend fun deleteProceduresByIds(id: Long) {
        procedureDao.deleteProceduresByIds(listOf(id))
    }

    override suspend fun deleteProceduresByIds(ids: List<Long>) {
        procedureDao.deleteProceduresByIds(ids)
    }

    private fun Person.toPersonEntity(): PersonEntity {
        return PersonEntity (
            id = this.id,
            name = this.name,
            imageId = this.imageId
        )
    }

    private fun PersonEntity.toPerson(): Person {
        return Person (
            id = this.id,
            name = this.name,
            imageId = this.imageId
        )
    }

    private fun Procedure.toProcedureEntity(person: Person): ProcedureEntity {
        return ProcedureEntity(
            id = this.id,
            imageId = this.imageId,
            title = this.title,
            personId = person.id,
            note = this.note,
            timesOfIntake = this.timesOfIntake,
            startDate = this.startDate,
            endDate = this.endDate
        )
    }

    private fun ProcedureEntity.toProcedure(person: Person): Procedure {
        return Procedure(
            id = this.id,
            imageId = this.imageId,
            title = this.title,
            person = person,
            note = this.note,
            timesOfIntake = this.timesOfIntake,
            startDate = this.startDate,
            endDate = this.endDate
        )
    }
}