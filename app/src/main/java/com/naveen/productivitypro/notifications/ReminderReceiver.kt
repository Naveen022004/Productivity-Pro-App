package com.naveen.productivitypro.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.naveen.productivitypro.MainActivity
import com.naveen.productivitypro.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Productivity reminder"
        val taskId = intent.getLongExtra(EXTRA_TASK_ID, 0L)
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, "Task reminders", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Reminders for scheduled productivity tasks"
            }
        )

        val openIntent = Intent(context, MainActivity::class.java)
        val contentIntent = android.app.PendingIntent.getActivity(
            context, taskId.toInt(), openIntent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_icon)
            .setContentTitle("Task reminder")
            .setContentText(title)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (android.os.Build.VERSION.SDK_INT < 33 ||
            androidx.core.content.ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(taskId.toInt(), notification)
        }
    }

    companion object {
        const val EXTRA_TASK_ID = "task_id"
        const val EXTRA_TITLE = "task_title"
        private const val CHANNEL_ID = "task_reminders"
    }
}
