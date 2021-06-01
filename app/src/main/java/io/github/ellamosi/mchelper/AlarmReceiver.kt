package io.github.ellamosi.mchelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "AlarmReceiver"
        const val ACTION = "io.github.ellamosi.mchelper.START_SERVICE_ALARM"
    }

    override fun onReceive(context: Context, intent: Intent) {
        logIntent(intent)
        if (intent.action.equals(ACTION, ignoreCase = true)) {
            val serviceIntent = Intent(context, MCHService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }

    private fun logIntent(intent : Intent) {
        val message = "Broadcast intent detected " + intent.action
        Log.d(TAG, message)
    }
}