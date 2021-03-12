package ru.andreyhoco.treatmentplan.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import ru.andreyhoco.treatmentplan.persistence.TreatmentPlanDbContract

@Entity(
    tableName = TreatmentPlanDbContract.ProceduresWithPerson.TABLE_NAME
)
data class ProcedureWithPersonEntity(
    @Embedded
    @ColumnInfo(name = TreatmentPlanDbContract.ProceduresWithPerson.COLUMN_NAME_PERSON)
    val person: PersonEntity,

    @Relation(
        parentColumn = TreatmentPlanDbContract.Persons.COLUMN_NAME_ID,
        entityColumn = TreatmentPlanDbContract.Procedures.COLUMN_NAME_PERSON_ID
    )
    val procedure: ProcedureEntity
)
