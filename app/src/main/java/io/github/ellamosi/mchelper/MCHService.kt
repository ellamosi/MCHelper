package io.github.ellamosi.mchelper

import android.content.Intent
import android.util.Log
import android.content.BroadcastReceiver
import android.content.Context
import java.util.Calendar
import android.app.AlarmManager
import android.app.PendingIntent
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

//        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent("io.github.ellamosi.musiccasthelper.ALARM")
//        val alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        val calendar = Calendar.getInstance()
//
//        alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.timeInMillis, 10000,  alarmIntent)

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
        Log.i(TAG, "Service onBind")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Service onDestroy")
        applicationContext.contentResolver.unregisterContentObserver(volumeObserver);
    }
}