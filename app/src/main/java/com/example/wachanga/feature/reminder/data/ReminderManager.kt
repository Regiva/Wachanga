package com.example.wachanga.feature.reminder.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.wachanga.util.isVersionLowerThanS
import javax.inject.Inject

class ReminderManager @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
) {

    fun startReminder(noteId: Long, noteContent: String) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("note_id", noteId)
            putExtra("note_content", noteContent)
        }.let { intent ->
            PendingIntent.getBroadcast(
                context.applicationContext,
                noteId.toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }

        if (canScheduleExactAlarms()) {
            // TODO:
            val triggerTimeInMillis = System.currentTimeMillis() + TEN_SECONDS_MILLIS
//            val triggerTimeInMillis = Calendar.getInstance().time.time + TEN_MINUTES_MILLIS
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTimeInMillis,
                intent,
            )
        }
    }

    fun stopReminder(noteId: Long) {
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context.applicationContext,
                noteId.toInt(),
                intent,
                0 or PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }

    private fun canScheduleExactAlarms(): Boolean {
        return if (isVersionLowerThanS()) {
            true
        } else {
            alarmManager.canScheduleExactAlarms()
        }
    }

    companion object {
        private const val TEN_MINUTES_MILLIS = 600_000
        private const val TEN_SECONDS_MILLIS = 10_000
    }
}
