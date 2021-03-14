package ru.andreyhoco.treatmentplan.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.andreyhoco.treatmentplan.persistence.database.TreatmentPlanDatabase
import ru.andreyhoco.treatmentplan.persistence.entities.PersonEntity
import ru.andreyhoco.treatmentplan.persistence.entities.ProcedureEntity
import ru.andreyhoco.treatmentplan.repository.modelEntities.*

class ProcedureAndPersonRepositoryImp(
        private val appDatabase: TreatmentPlanDatabase
) : ProcedureAndPersonRepository {
    private val personDao = appDatabase.personDao
    private val procedureDao = appDatabase.procedureDao

    override fun getAllPerson(): Flow<List<Person>> {
        return personDao.getAllPersons().map { entityList ->
            entityList.map { personEntity ->
                personEntity.toPerson()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllPersonOneShot(): List<Person> {
        return personDao.getAllPersonsOneShot().map { personEntity ->
            personEntity.toPerson()
        }
    }

    override suspend fun getAllProceduresOneShot(): List<Procedure> {
        return procedureDao.getAllProceduresOneShot().map { procedureEntity ->
                val personEntity = personDao.getOneShotPersonById(procedureEntity.personId)
                procedureEntity.toProcedure(personEntity.toPerson())
            }
    }

    override fun getPersonById(id: Long): Flow<Person> {
        return personDao.getPersonById(id).map { personEntity ->
                personEntity.toPerson()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertPerson(person: Person) {
        personDao.insert(person.toPersonEntity())
    }

    override suspend fun insertProcedure(procedure: Procedure) {
        procedureDao.insert(procedure.toProcedureEntity(procedure.person))

//        Добавить пуск воркера с нотификацией
    }

    suspend fun insertProcedures(procedures: List<Procedure>) {
        procedureDao.insertProcedures(procedures.map { it.toProcedureEntity(it.person) })
    }

    override fun getAllProcedures(): Flow<List<Procedure>> {
        return procedureDao.getAllProcedures().map { procedureList ->
            procedureList.map { procedureEntity ->
                val personEntity = personDao.getOneShotPersonById(procedureEntity.personId)
                procedureEntity.toProcedure(personEntity.toPerson())
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getProcedureById(id: Long): Flow<Procedure> {
        return procedureDao.getProcedureById(id).map { procedureEntity ->
            val personEntity = personDao.getOneShotPersonById(procedureEntity.id)
            procedureEntity.toProcedure(personEntity.toPerson())
        }.flowOn(Dispatchers.IO)
    }

    override fun getProcedureGroupsBetweenDates(
        firstDate: Long,
        secondDate: Long
    ): Flow<List<IntakeProcedureTimeGroup>> {
        return procedureDao.getProceduresBetweenDates(firstDate, secondDate).map { entitiesList ->
            Log.d("FIX", "${entitiesList.map { it.id }}")
            val proceduresList = entitiesList.map {
                Log.d("FIX", "${entitiesList.map { it.id }}")
                Log.d("FIX", "${entitiesList.size}")
                val person = personDao.getOneShotPersonById(it.personId).toPerson()
                it.toProcedure(person)
            }
            groupProceduresByTime(proceduresList)

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getProcedureGroupsBetweenDatesOneShot(
        firstDate: Long,
        secondDate: Long
    ): List<IntakeProcedureTimeGroup> {
        val procedures = procedureDao.getProceduresBetweenDatesOneShot(firstDate, secondDate).map {
            val person = personDao.getOneShotPersonById(it.personId).toPerson()
            it.toProcedure(person)
        }
        return groupProceduresByTime(procedures).filter { timeGroup ->
            (timeGroup.startTime >= firstDate) and (timeGroup.endTime <= secondDate)
        }
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

    private fun groupProceduresByTime(
        list: List<Procedure>,
        maxGroupTimeSize: Long = 3600000,
        maxGroupTimeDistance: Long = 900000
    ): List<IntakeProcedureTimeGroup> {

        val listWithTime = list.map{ procedure ->
            procedure.timesOfIntake.map { timeOfIntake ->
                IntakeProcedure(
                    id = procedure.id,
                    imageId = procedure.imageId,
                    title = procedure.title,
                    person = procedure.person,
                    note = procedure.note,
                    timeOfIntake = timeOfIntake,
                    startDate = procedure.startDate,
                    endDate = procedure.endDate
                )
            }
        }.flatten().sortedBy { it.timeOfIntake.timeOfTakes }

        var index = 0
        val groups = mutableListOf<IntakeProcedureTimeGroup>()
        var startTime: Long
        var headElement: IntakeProcedure

        while (index in listWithTime.indices) {
            //Создание врееменной группы
            val timeGroup = mutableListOf<IntakeProcedure>()

            //Выделение головного элемента группы
            headElement = listWithTime[index]
            //Начальное время группы
            startTime = headElement.timeOfIntake.timeOfTakes

            //Добавление в группу головного элемента
            timeGroup.add(headElement)

            //Переход к следующему элементу
            index++

            while (index < listWithTime.size) {
                //Вычисление дистанции по времени мужду соседними процедурами
                val diff = listWithTime[index].timeOfIntake.timeOfTakes - timeGroup.last().timeOfIntake.timeOfTakes

                if ((listWithTime[index].timeOfIntake.timeOfTakes <= (startTime + maxGroupTimeSize))
                        and (diff <= maxGroupTimeDistance)
                ) {
                    timeGroup.add(listWithTime[index])
                    index++
                } else {
                    break
                }
            }

            groups.add(
                IntakeProcedureTimeGroup(
                    startTime = startTime,
                    endTime = timeGroup.last().timeOfIntake.timeOfTakes,
                    procedures = timeGroup
                )
            )
        }

        return groups
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