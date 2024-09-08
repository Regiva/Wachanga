package com.example.wachanga.feature.reminder.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.wachanga.MainActivity
import com.example.wachanga.R
import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.usecase.DeleteReminderUseCase
import com.example.wachanga.feature.reminder.data.AlarmReceiver.Companion.NOTIFICATION_ID
import com.example.wachanga.feature.reminder.data.AlarmReceiver.Companion.PENDING_INTENT_REQUEST_CODE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var deleteReminderUseCase: DeleteReminderUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val title = context.getString(R.string.reminder_notification_note_title)
        val content = intent.getStringExtra("note_content")
            ?: context.getString(R.string.reminder_notification_note_content)
        val noteId = intent.getIntExtra("note_id", -1)
        sendReminderNotification(context, title, content)
        deleteReminder(noteId, content)
    }

    private fun deleteReminder(noteId: Int, content: String) {
        if (noteId != -1) {
            goAsync {
                deleteReminderUseCase(Note(noteId.toLong(), content))
            }
        }
    }

    private fun sendReminderNotification(
        context: Context,
        title: String,
        content: String,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.sendReminderNotification(
            context = context,
            channelId = context.getString(R.string.reminder_notification_channel_id),
            title = title,
            content = content,
        )
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val PENDING_INTENT_REQUEST_CODE = 1
    }
}

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend CoroutineScope.() -> Unit,
) {
    val pendingResult = goAsync()
    CoroutineScope(SupervisorJob()).launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}

fun NotificationManager.sendReminderNotification(
    context: Context,
    channelId: String,
    title: String,
    content: String,
) {
    val contentIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        PENDING_INTENT_REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setStyle(NotificationCompat.BigTextStyle().bigText(content))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}
