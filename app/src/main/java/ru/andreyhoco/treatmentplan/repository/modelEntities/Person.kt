package ru.andreyhoco.treatmentplan.repository.modelEntities

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: Long,
    val name: String,
    val imageId: Int
)