package ru.andreyhoco.treatmentplan.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.andreyhoco.treatmentplan.persistence.Converters
import ru.andreyhoco.treatmentplan.persistence.TreatmentPlanDbContract
import ru.andreyhoco.treatmentplan.persistence.daos.PersonDao
import ru.andreyhoco.treatmentplan.persistence.daos.ProcedureDao
import ru.andreyhoco.treatmentplan.persistence.entities.PersonEntity
import ru.andreyhoco.treatmentplan.persistence.entities.ProcedureEntity

@Database(entities = [
    PersonEntity::class,
    ProcedureEntity::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class TreatmentPlanDatabase: RoomDatabase() {

    abstract val personDao: PersonDao
    abstract val procedureDao: ProcedureDao

    companion object {
        fun create(
                appContext: Context
        ): TreatmentPlanDatabase {
            val dbInstance = Room.databaseBuilder(
                appContext,
                TreatmentPlanDatabase::class.java,
                TreatmentPlanDbContract.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

            return dbInstance
        }
    }

}