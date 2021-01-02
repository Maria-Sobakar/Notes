package com.marias.android.notes.screens.notification

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*

class TimePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->

            setFragmentResult(TIME_HOUR_REQUEST_KEY,Bundle().apply {
                putInt(TIME_HOUR_REQUEST_KEY,hourOfDay)
                putInt(TIME_MIN_REQUEST_KEY, minute)
            })
        }
        val calendar = Calendar.getInstance()
        val initialHour = calendar.get(Calendar.HOUR_OF_DAY)
        val initialMinute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHour,
            initialMinute,
            true
        )
    }
    companion object{
        const val TIME_HOUR_REQUEST_KEY = "timeHourRequestKey"
        const val TIME_MIN_REQUEST_KEY = "timeMinRequestKey"

    }
}