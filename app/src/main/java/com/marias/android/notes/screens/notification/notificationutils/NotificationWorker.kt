package com.marias.android.notes.screens.notification.notificationutils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.marias.android.notes.CHANNEL_ID
import com.marias.android.notes.MainActivity
import com.marias.android.notes.R

class NotificationWorker(
    val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        val text = inputData.getString(CONTENT_TEXT_KEY)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Notes")
            .setContentText(text)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_baseline_alarm)
        val mainIntent = Intent(context, MainActivity::class.java)
        val activity = PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            mainIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        builder.setContentIntent(activity)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, builder.build())

        return Result.success()
    }

    companion object {
        const val REQUEST_CODE = 0
        const val CONTENT_TEXT_KEY = "contentText"
        const val NOTIFICATION_ID = 1
    }
}