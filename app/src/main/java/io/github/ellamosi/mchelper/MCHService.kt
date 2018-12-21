package io.github.ellamosi.mchelper

import android.content.Intent
import android.util.Log
import android.app.Service
import android.content.IntentFilter
import android.os.Handler
import android.os.IBinder
import io.github.ellamosi.mchelper.actions.*

class MCHService : Service() {
    companion object {
        private const val TAG = "MchService"
    }

    private val worker = MCHWorker()
    private val workerThread = Thread(worker)
    private val volumeHandler = Handler()
    private var volumeObserver : VolumeObserver? = null
    private val screenReceiver = ScreenReceiver()
    private val screenStateFilter = IntentFilter()

    override fun onCreate() {
        super.onCreate()
        volumeObserver = VolumeObserver(worker, volumeHandler)
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON)
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF)
        application.registerReceiver(screenReceiver, screenStateFilter)
        applicationContext.contentResolver.registerContentObserver(
                android.provider.Settings.System.CONTENT_URI,
                true,
                volumeObserver
        )
        workerThread.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extraAction = intent?.getStringExtra("ACTION")
        Log.i(TAG, "MCHService started (action: '${intent?.action}', extraAction: '$extraAction')")

        when (extraAction) {
            "SCREEN_OFF" -> worker.enqueueAction(TurnOff())
            "SCREEN_ON"  -> worker.enqueueAction(TurnOn())
        }

        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.contentResolver.unregisterContentObserver(volumeObserver)
    }
}