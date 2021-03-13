package ru.andreyhoco.treatmentplan.repository.modelEntities

data class Procedure(
    val id: Long,
    val imageId: Int,
    val title: String,
    val person: Person,
    val note: String,
    val timesOfTaking: List<Long>,
    val startDate: Long,
    val endDate: Long
)
