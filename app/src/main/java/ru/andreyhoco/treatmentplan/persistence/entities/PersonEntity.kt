package ru.andreyhoco.treatmentplan.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.andreyhoco.treatmentplan.persistence.TreatmentPlanDbContract

@Entity(
    tableName = TreatmentPlanDbContract.Persons.TABLE_NAME
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TreatmentPlanDbContract.Persons.COLUMN_NAME_ID)
    val id: Long,

    @ColumnInfo(name = TreatmentPlanDbContract.Persons.COLUMN_NAME_PERSON_NAME)
    val name: String,

    @ColumnInfo(name = TreatmentPlanDbContract.Persons.COLUMN_NAME_IMAGE_ID)
    val imageId: Int
)
