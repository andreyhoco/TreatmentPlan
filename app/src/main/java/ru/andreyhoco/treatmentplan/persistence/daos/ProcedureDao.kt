package ru.andreyhoco.treatmentplan.persistence.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.andreyhoco.treatmentplan.persistence.entities.ProcedureEntity

@Dao
interface ProcedureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(procedure: ProcedureEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProcedures(procedures: List<ProcedureEntity>)

    @Query("SELECT * FROM Procedures WHERE procedure_id == :procedureId")
    @Transaction
    fun getProcedureById(procedureId: Long): Flow<ProcedureEntity>

    @Query("SELECT * FROM Procedures")
    fun getAllProcedures(): Flow<List<ProcedureEntity>>

    @Query(
            "SELECT * From Procedures WHERE (:firstDate BETWEEN start_date AND end_date)" +
                    " OR (:secondDate BETWEEN start_date AND end_date)"
    )
    fun getProceduresBetweenDates(firstDate: Long, secondDate: Long): Flow<ProcedureEntity>

    @Query("DELETE FROM Procedures")
    suspend fun deleteAllProcedures()

    @Query(
            "DELETE FROM Procedures WHERE (start_date BETWEEN :firstDate AND :secondDate) " +
            "AND (end_date BETWEEN :firstDate AND :secondDate)"
    )
    suspend fun deleteProceduresBetweenDates(firstDate: Long, secondDate: Long)

    @Query("DELETE FROM Procedures WHERE procedure_id IN (:procedureIds)")
    suspend fun deleteProceduresByIds(procedureIds: List<Long>)
}