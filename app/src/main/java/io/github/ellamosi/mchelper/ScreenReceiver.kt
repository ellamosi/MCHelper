package io.github.ellamosi.mchelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ScreenReceiver : BroadcastReceiver() {
    private val TAG = "ScreenReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        logIntent(intent)
        when (intent.action) {
            Intent.ACTION_SCREEN_ON -> screenOn()
            Intent.ACTION_SCREEN_OFF -> screenOff()
        }
    }

    private fun screenOn() {
        Thread {
            MCApi.setTvInput()
            MCApi.turnOn()
        }.start()
    }

    private fun screenOff() {
        Thread {
            if (MCApi.isOnTvInput()) MCApi.turnOff()
        }.start()
    }

    private fun logIntent(intent : Intent) {
        val message = "Broadcast intent detected " + intent.action
        Log.d(TAG, message)
    }
}