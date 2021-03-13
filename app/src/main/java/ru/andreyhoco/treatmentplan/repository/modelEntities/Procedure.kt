package ru.andreyhoco.treatmentplan.repository.modelEntities

data class Procedure(
    val id: Long,
    val imageId: Int,
    val title: String,
    var person: Person,
    val note: String,
    var timesOfIntake: List<TimeOfIntake>,
    var startDate: Long,
    var endDate: Long
)
