package ru.andreyhoco.treatmentplan.persistence.entities

import androidx.room.*
import ru.andreyhoco.treatmentplan.persistence.TreatmentPlanDbContract

@Entity(
    tableName = TreatmentPlanDbContract.Procedures.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = PersonEntity::class,
        parentColumns = arrayOf(TreatmentPlanDbContract.Persons.COLUMN_NAME_ID),
        childColumns = arrayOf(TreatmentPlanDbContract.Procedures.COLUMN_NAME_ID),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ProcedureEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_ID)
    val id: Long,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_IMAGE_ID)
    val imageId: Int,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_PERSON_ID)
    val personId: Long,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_NOTE)
    val note: String,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_TIMES_OF_TAKING)
    val timesOfTaking: List<Long>,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_START_DATE)
    val startDate: Long,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_END_DATE)
    val endDate: Long
)
