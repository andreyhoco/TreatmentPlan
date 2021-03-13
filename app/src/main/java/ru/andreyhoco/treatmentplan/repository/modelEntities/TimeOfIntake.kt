package ru.andreyhoco.treatmentplan.repository.modelEntities

data class TimeOfIntake(
        val timeOfTakes: Long,
        val isDone: Boolean
) {
    override fun toString(): String {
        return "$isDone*$timeOfTakes"
    }
}
