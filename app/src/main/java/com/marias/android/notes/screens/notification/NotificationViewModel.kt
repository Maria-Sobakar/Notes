package com.marias.android.notes.screens.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.marias.android.notes.screens.notification.notificationutils.NotificationWorker
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationViewModel(var notificationText: String) : ViewModel() {

    private lateinit var targetDate: Calendar
    private lateinit var targetTime: Calendar
    val closeLiveData = MutableLiveData<Boolean>()

    fun createNotification() {
        val year = targetDate.get(Calendar.YEAR)
        val month = targetDate.get(Calendar.MONTH)
        val day = targetDate.get(Calendar.DAY_OF_MONTH)
        val hour = targetTime.get(Calendar.HOUR_OF_DAY)
        val minute = targetTime.get(Calendar.MINUTE)
        val targetMoment = Calendar.getInstance()
        targetMoment.set(year,month,day,hour,minute,0)
        val time = targetMoment.timeInMillis
        if (time > System.currentTimeMillis()) {
            val delay = time - System.currentTimeMillis()
            val data = Data.Builder()
            data.putString(NotificationWorker.CONTENT_TEXT_KEY, notificationText)

            val workRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data.build())
                .build()
            WorkManager.getInstance().enqueue(workRequest)
        }
        closeLiveData.value = true
    }

    fun notificationTextInputted(text: String) {
        notificationText = text
    }

    fun dateIsSet(date: Calendar) {
        targetDate = date
    }

    fun timeIsSet(time:Calendar){
        targetTime = time
    }

    class NotificationViewModelFactory(private val notificationText: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NotificationViewModel(notificationText) as T
        }
    }
}
