package io.github.ellamosi.mchelper

import android.content.Intent
import android.util.Log
import android.app.Service
import android.content.IntentFilter
import android.os.Handler
import android.os.IBinder

class MCHService : Service() {
    private val TAG = "MchService"
    var volumeObserver : VolumeObserver? = null
    var volumeHandler : Handler? = null

    override fun onCreate() {
        super.onCreate()
        volumeHandler = Handler()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "MCHService started")

        val screenReceiver = ScreenReceiver()
        val screenStateFilter = IntentFilter()
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON)
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF)
        application.registerReceiver(screenReceiver, screenStateFilter)

        volumeObserver = volumeHandler?.let { VolumeObserver(this, it) }
        applicationContext.contentResolver.registerContentObserver(
                android.provider.Settings.System.CONTENT_URI,
                true,
                volumeObserver
        )

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.contentResolver.unregisterContentObserver(volumeObserver);
    }
}