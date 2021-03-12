package ru.andreyhoco.treatmentplan.persistence

object TreatmentPlanDbContract {
    const val DATABASE_NAME = "TreatmentPlan.db"

    object Procedures {
        const val TABLE_NAME = "Procedures"

        const val COLUMN_NAME_ID = "procedure_id"
        const val COLUMN_NAME_IMAGE_ID = "image_id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_PERSON = "person"
        const val COLUMN_NAME_NOTE = "note"
        const val COLUMN_NAME_TIMES_OF_TAKING = "times_of_taking"
        const val COLUMN_NAME_START_DATE = "start_date"
        const val COLUMN_NAME_END_DATE = "end_date"
    }

    object Persons {
        const val TABLE_NAME = "Persons"

        const val COLUMN_NAME_ID = "person_id"
        const val COLUMN_NAME_PERSON_NAME = "name"
        const val COLUMN_NAME_IMAGE_ID = "image_id"
    }
}