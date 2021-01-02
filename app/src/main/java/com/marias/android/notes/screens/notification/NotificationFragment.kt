package com.marias.android.notes.screens.notification


import android.os.Bundle
import android.os.Parcel
import android.text.format.DateFormat

import android.view.View

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.marias.android.notes.R

import kotlinx.android.synthetic.main.notification_fragment.*
import java.time.Clock

import java.util.*
import kotlin.concurrent.fixedRateTimer


class NotificationFragment : Fragment(R.layout.notification_fragment) {

    private val viewModel: NotificationViewModel by viewModels {
        val text = arguments?.getString(ARG_TEXT_ID) as String
        NotificationViewModel.NotificationViewModelFactory(text)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationEditText.setText(viewModel.notificationText)

        datePickButton.setOnClickListener {

            val dateValidator = object : CalendarConstraints.DateValidator {
                override fun describeContents(): Int {
                    return 0
                }

                override fun writeToParcel(dest: Parcel?, flags: Int) {
                }

                override fun isValid(date: Long): Boolean {
                    val minDate = Calendar.getInstance()
                    minDate.set(Calendar.HOUR_OF_DAY, 0)
                    minDate.set(Calendar.MINUTE, 0)
                    minDate.set(Calendar.SECOND, 0)
                    return date >= minDate.timeInMillis
                }
            }
            val calendarConstraints = CalendarConstraints.Builder()
            calendarConstraints.setValidator(dateValidator)
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setCalendarConstraints(calendarConstraints.build())
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener {
                val targetDate = Calendar.getInstance()
                targetDate.time = Date(it)
                viewModel.dateIsSet(targetDate)
                datePickButton.text = DateFormat.format("dd.MM.yyyy", targetDate)
            }
            picker.show(parentFragmentManager, DIALOG_DATE)
        }
        timePickButton.setOnClickListener {
            val builder = MaterialTimePicker.Builder()
            builder.setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            builder.setTimeFormat(TimeFormat.CLOCK_24H)
            builder.setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
            builder.setMinute(Calendar.getInstance().get(Calendar.MINUTE))
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener {
                val hour = picker.hour
                val minute = picker.minute
                val targetTime = Calendar.getInstance()
                targetTime.set(0, 0, 0, hour, minute, 0)
                viewModel.timeIsSet(targetTime)
                timePickButton.text = DateFormat.format("kk:mm", targetTime)
            }
            picker.show(parentFragmentManager, DIALOG_TIME)
        }
        notificationEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.notificationTextInputted(text.toString())
        }
        confirmNotificationFab.setOnClickListener {
            viewModel.createNotification()
        }

        viewModel.closeLiveData.observe(viewLifecycleOwner) { value ->
            if (value) {
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.navigation_host_fragment)
                navController.popBackStack()
            }
        }
    }

    companion object {
        private const val DIALOG_DATE = "DialogDate"
        private const val DIALOG_TIME = "DialogTime"
        const val ARG_TEXT_ID = "TextKey"
    }
}