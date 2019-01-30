package io.github.ellamosi.mchelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.support.v4.content.ContextCompat.startForegroundService

class ScreenReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "ScreenReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        logIntent(intent)
        when (intent.action) {
            Intent.ACTION_SCREEN_ON -> screenOn(context)
            Intent.ACTION_SCREEN_OFF -> screenOff(context)
        }
    }

    private fun screenOn(context: Context) {
        val intent = Intent(context, MCHService::class.java)
        intent.putExtra("ACTION", "SCREEN_ON")
        startForegroundService(context, intent)
    }

    private fun screenOff(context: Context) {
        val intent = Intent(context, MCHService::class.java)
        intent.putExtra("ACTION", "SCREEN_OFF")
        startForegroundService(context, intent)
    }

    private fun logIntent(intent : Intent) {
        val message = "Broadcast intent detected " + intent.action
        Log.d(TAG, message)
    }
}