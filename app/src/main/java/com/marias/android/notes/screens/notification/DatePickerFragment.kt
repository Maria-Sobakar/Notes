package com.marias.android.notes.screens.notification

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*

class DatePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            setFragmentResult(DATE_YEAR_REQUEST_KEY, Bundle().apply {
                putInt(DATE_YEAR_REQUEST_KEY,year)
                putInt(DATE_MONTH_REQUEST_KEY, month)
                putInt(DATE_DAY_REQUEST_KEY, dayOfMonth)
            })
        }
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object {

        const val DATE_YEAR_REQUEST_KEY = "dateYearRequestKey"
        const val DATE_MONTH_REQUEST_KEY = "dateMonthRequestKey"
        const val DATE_DAY_REQUEST_KEY = "dateDayRequestKey"
    }


}