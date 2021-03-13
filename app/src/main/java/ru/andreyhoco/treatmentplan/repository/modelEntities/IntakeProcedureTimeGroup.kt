package ru.andreyhoco.treatmentplan.repository.modelEntities

data class IntakeProcedureTimeGroup(
    val startTime: Long,
    val endTime: Long,
    val procedures: List<IntakeProcedure>
)
