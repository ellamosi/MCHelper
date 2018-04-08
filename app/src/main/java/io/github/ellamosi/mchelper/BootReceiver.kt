package io.github.ellamosi.mchelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        logIntent(context, intent)
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
            logIntent(context, intent)
            val serviceIntent = Intent(context, MCHService::class.java)
            context.startService(serviceIntent)
        }
    }

    private fun logIntent(context: Context, intent : Intent) {
        val message = "Broadcast intent detected " + intent.action
        Log.i("BootReceiver", message)
        //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}