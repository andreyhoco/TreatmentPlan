package ru.andreyhoco.treatmentplan.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.andreyhoco.treatmentplan.persistence.entities.PersonEntity

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: PersonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<PersonEntity>)

    @Query("SELECT * FROM Persons WHERE person_id == :id")
    fun getPersonById(id: Long): Flow<PersonEntity>

    @Query("SELECT * FROM Persons")
    fun getAllPersons(): Flow<List<PersonEntity>>

    @Query("DELETE FROM Persons WHERE person_id IN (:personIds)")
    suspend fun deletePersonsByIds(personIds: List<Long>)
}