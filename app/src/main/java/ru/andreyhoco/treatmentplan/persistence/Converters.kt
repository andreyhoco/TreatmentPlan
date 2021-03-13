package ru.andreyhoco.treatmentplan.persistence

import androidx.room.TypeConverter
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake

class Converters {
    @TypeConverter
    fun fromListOfTimeOfIntake(list: List<TimeOfIntake>): String {
        return list.joinToString(separator = "|")
    }

    @TypeConverter
    fun fromStringToListTimeOfIntake(string: String): List<TimeOfIntake> {
        return string.split("|").map {
            TimeOfIntake(
                it.substringAfter("*").toLong(),
                it.substringBefore("*").toBoolean()
            )
        }
    }
}