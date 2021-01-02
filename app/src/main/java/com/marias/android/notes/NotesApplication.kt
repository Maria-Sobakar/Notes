package com.marias.android.notes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.marias.android.notes.data.database.DatabaseBuilder
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.screens.notification.NotificationViewModel

const val CHANNEL_NAME = "notes-notification"
const val DESCRIPTION = "Notification channel for Notes app."
const val CHANNEL_ID = "Channel-Id-$CHANNEL_NAME"

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseBuilder.initialize(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = DESCRIPTION
            channel.setShowBadge(true)

            val notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}