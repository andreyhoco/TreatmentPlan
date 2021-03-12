package ru.andreyhoco.treatmentplan.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.andreyhoco.treatmentplan.persistence.TreatmentPlanDbContract

@Entity(
    tableName = TreatmentPlanDbContract.Procedures.TABLE_NAME
)
data class ProcedureEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_ID)
    val id: Long,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_IMAGE_ID)
    val imageId: Int,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_PERSON)
    @Embedded
    val person: PersonEntity,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_NOTE)
    val note: String,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_TIMES_OF_TAKING)
    val timesOfTaking: List<Long>,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_START_DATE)
    val startDate: Long,

    @ColumnInfo(name = TreatmentPlanDbContract.Procedures.COLUMN_NAME_END_DATE)
    val endDate: Long
)
