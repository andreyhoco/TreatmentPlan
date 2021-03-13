package ru.andreyhoco.treatmentplan.presentation.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()

        return TimePickerDialog(
            requireContext(),
            this,
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            true
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val bundle = Bundle()

        bundle.putInt(BUNDLE_KEY_HOUR, hourOfDay)
        bundle.putInt(BUNDLE_KEY_MINUTE, minute)

        setFragmentResult(KEY_RESULT, bundle)
    }

    companion object {
        const val KEY_RESULT = "TIME_FROM_TIME_PICKER"
        const val BUNDLE_KEY_HOUR = "HOUR"
        const val BUNDLE_KEY_MINUTE = "MINUTE"
    }
}