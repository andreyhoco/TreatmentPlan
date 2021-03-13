package ru.andreyhoco.treatmentplan.repository.modelEntities

data class ProcedureTimeGroup(
    val startTime: Long,
    val endTime: Long,
    val procedures: List<Procedure>
)
