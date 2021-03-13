package ru.andreyhoco.treatmentplan.persistence

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListOfLong(list: List<Long>): String {
        val newStr = list.joinToString(separator = "|")
        return newStr
    }

    @TypeConverter
    fun fromString(stringWithList: String): List<Long> {
        val list = stringWithList.split("|").map { it.toLong() }
        return list
    }
}