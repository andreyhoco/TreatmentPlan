package ru.andreyhoco.treatmentplan.repository.modelEntities

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.andreyhoco.treatmentplan.persistence.database.TreatmentPlanDatabase
import ru.andreyhoco.treatmentplan.repository.ProcedureAndPersonRepository

class ProcedureAndPersonRepositoryImp(
        private val appContext: Context,
        private val appDatabase: TreatmentPlanDatabase
) : ProcedureAndPersonRepository {
    val personDao = appDatabase.personDao
    val procedureDao = appDatabase.procedureDao

    override fun getAllPerson(): Flow<List<Person>> {
        TODO("Not yet implemented")
    }

    override fun getPersonById(id: Long): Flow<Person> {
        TODO("Not yet implemented")
    }

    override fun insertPerson(person: Person) {
        TODO("Not yet implemented")
    }

    override fun getAllProcedures(): Flow<List<Procedure>> {
        TODO("Not yet implemented")
    }

    override fun getProcedureById(id: Int): Flow<Procedure> {
        TODO("Not yet implemented")
    }

    override fun getProcedureGroupsByDate(date: Long): Flow<List<ProcedureTimeGroup>> {
        TODO("Not yet implemented")
    }

    override fun insertProcedure(procedure: Procedure) {
        TODO("Not yet implemented")
    }
}