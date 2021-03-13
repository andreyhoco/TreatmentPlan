package ru.andreyhoco.treatmentplan.repository.modelEntities

import kotlinx.serialization.Serializable

@Serializable
data class TimeOfIntake(
        val timeOfTakes: Long,
        val isDone: Boolean
)
