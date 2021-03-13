package ru.andreyhoco.treatmentplan.repository.modelEntities

data class IntakeProcedure(
    val id: Long,
    val imageId: Int,
    val title: String,
    val person: Person,
    val note: String,
    val timeOfIntake: TimeOfIntake,
    val startDate: Long,
    val endDate: Long
)
