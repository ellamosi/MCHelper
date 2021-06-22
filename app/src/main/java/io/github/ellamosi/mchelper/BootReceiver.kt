package io.github.ellamosi.mchelper

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*
import androidx.core.content.ContextCompat.startForegroundService


class BootReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "BootReceiver"
        private const val alarmInterval: Long = 2 * 60 * 1000 // 2 minutes (min accepted)
    }

    override fun onReceive(context: Context, intent: Intent) {
        logIntent(intent)
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
            logIntent(intent)
            val alarmManager = context.getSystemService(Service.ALARM_SERVICE) as AlarmManager

            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            alarmIntent.action = AlarmReceiver.ACTION
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC,
                Calendar.getInstance().timeInMillis,
                alarmInterval,
                pendingIntent
            )

            val serviceIntent = Intent(context, MCHService::class.java)
            startForegroundService(context, serviceIntent)
        }
    }

    private fun logIntent(intent: Intent) {
        val message = "Broadcast intent detected " + intent.action
        Log.d(TAG, message)
    }
}