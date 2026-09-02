package com.naveen.productivitypro.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.naveen.productivitypro.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tasks = AppDatabase.get(context).taskDao().getPendingWithReminders()
                tasks.forEach { ReminderScheduler.schedule(context, it) }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
