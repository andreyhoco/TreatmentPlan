package ru.andreyhoco.treatmentplan.presentation.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*

class DatePickerFragment:
    DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private var key: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()

        if (key == null) {
            key = savedInstanceState?.getString(STATE_KEY, KEY_RESULT_DATE_START)
                ?: KEY_RESULT_DATE_END
        }

        return DatePickerDialog(
            requireContext(),
            this,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val bundle = Bundle()
        bundle.putInt(BUNDLE_KEY_YEAR, year)
        bundle.putInt(BUNDLE_KEY_MONTH, month)
        bundle.putInt(BUNDLE_KEY_DAY_OF_MONTH, dayOfMonth)

        setFragmentResult(key ?: KEY_RESULT_DATE_START, bundle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(STATE_KEY, key)
    }

    companion object {
        const val KEY_RESULT_DATE_START = "DATE_START_FROM_DATE_PICKER"
        const val KEY_RESULT_DATE_END = "DATE_END_FROM_DATE_PICKER"
        const val BUNDLE_KEY_YEAR = "YEAR"
        const val BUNDLE_KEY_MONTH = "MONTH"
        const val BUNDLE_KEY_DAY_OF_MONTH = "DAY_OF_MONTH"
        const val STATE_KEY = "STATE_KEY"

        fun newInstance(key: String): DatePickerFragment {
            val datePicker = DatePickerFragment()

            datePicker.key = key

            return datePicker
        }
    }
}