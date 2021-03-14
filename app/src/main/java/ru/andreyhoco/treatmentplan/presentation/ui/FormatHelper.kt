package ru.andreyhoco.treatmentplan.presentation.ui

import java.util.*

class FormatHelper {
    companion object {
        fun getFormattedDate(timeInMillis: Long): String {
            val c = Calendar.getInstance()

            c.timeInMillis = timeInMillis

            return String.format("%1$" + "td.%1$" + "tm.%1$" + "tY", c)
        }

        fun getFormattedTime(timeInMillis: Long): String {
            val c = Calendar.getInstance()

            c.timeInMillis = timeInMillis

            return String.format("%1$" + "tH:%1$" + "tM", c)
        }
    }
}