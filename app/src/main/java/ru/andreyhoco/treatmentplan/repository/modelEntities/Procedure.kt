package ru.andreyhoco.treatmentplan.repository.modelEntities

data class Procedure(
    val id: Long,
    val imageId: Int,
    var title: String,
    var person: Person,
    var note: String,
    var timesOfIntake: List<TimeOfIntake>,
    var startDate: Long,
    var endDate: Long
)
