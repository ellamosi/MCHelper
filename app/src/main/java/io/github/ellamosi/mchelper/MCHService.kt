package io.github.ellamosi.mchelper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.util.Log
import android.view.Display
import io.github.ellamosi.mchelper.actions.TurnOff
import io.github.ellamosi.mchelper.actions.TurnOn


class MCHService : Service() {
    companion object {
        private const val TAG = "MchService"
        private const val FOREGROUND_ID = 1337
        private const val CHANNEL_ID = "service_notifications"
    }

    private val worker = MCHWorker()
    private val workerThread = Thread(worker)
    private val volumeHandler = Handler()
    private var volumeObserver : VolumeObserver? = null
    private val screenReceiver = ScreenReceiver()
    private val screenStateFilter = IntentFilter()
    private var displayManager: DisplayManager? = null

    override fun onCreate() {
        super.onCreate()
        volumeObserver = VolumeObserver(worker, volumeHandler)
        displayManager = applicationContext.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager?
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON)
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF)
        application.registerReceiver(screenReceiver, screenStateFilter)
        applicationContext.contentResolver.registerContentObserver(
                android.provider.Settings.System.CONTENT_URI,
                true,
                volumeObserver!!
        )
        createNotificationChannel()
        workerThread.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extraAction = intent?.getStringExtra("ACTION")
        Log.i(TAG, "MCHService started (action: '${intent?.action}', extraAction: '$extraAction')")

        when (extraAction) {
            "SCREEN_OFF" -> worker.enqueueAction(TurnOff())
            "SCREEN_ON"  -> turnOnIfDisplayOn()
        }

        val notification = buildForegroundNotification()

        startForeground(FOREGROUND_ID, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.contentResolver.unregisterContentObserver(volumeObserver!!)
    }

    private fun buildForegroundNotification(): Notification {
        val b = NotificationCompat.Builder(applicationContext, CHANNEL_ID)

        b.setOngoing(true)
                .setContentTitle(applicationContext.getText(R.string.notification_title))
                .setContentText(applicationContext.getText(R.string.notification_message))
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setTicker(applicationContext.getText(R.string.ticker_text))

        return b.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Service Notifications Channel",
                NotificationManager.IMPORTANCE_MIN
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(serviceChannel)
        }
    }

    private fun turnOnIfDisplayOn() {
        if (isDisplayOn()) worker.enqueueAction(TurnOn())
    }

    private fun isDisplayOn() : Boolean {
        for (display in displayManager!!.displays) {
            if (display.state == Display.STATE_ON) return true
        }
        return false
    }
}