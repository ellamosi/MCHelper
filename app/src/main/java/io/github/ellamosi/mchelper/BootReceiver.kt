package io.github.ellamosi.mchelper

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

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
            val alarmIntent = Intent(AlarmReceiver.ACTION)
            val pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val calendar = Calendar.getInstance()

            alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.timeInMillis, alarmInterval, pendingIntent)

            val serviceIntent = Intent(context, MCHService::class.java)
            context.startService(serviceIntent)
        }
    }

    private fun logIntent(intent: Intent) {
        val message = "Broadcast intent detected " + intent.action
        Log.d(TAG, message)
    }
}