package ru.andreyhoco.treatmentplan.repository

import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure

interface ProcedureAndPersonRepository {
    fun getProcedureById(id: Int) : Procedure

    fun getProceduresByDate(date: Long) : List<Procedure>

    fun getAllProcedures() : List<Procedure>

    fun getPersonById(id: Long) : Person

    fun getAllPerson() : List<Person>

    fun insertProcedure(procedure: Procedure)

    fun insertPerson(person: Person)
}