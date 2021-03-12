package ru.andreyhoco.treatmentplan.repository.modelEntities

data class Procedure(
    val id: Long,
    val title: String,
    val person: String,
    val note: String,
    val timesOfTaking: List<Long>,
    val startDate: Long,
    val endDate: Long
)
