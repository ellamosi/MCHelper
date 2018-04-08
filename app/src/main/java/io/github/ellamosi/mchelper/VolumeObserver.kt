package io.github.ellamosi.mchelper

import android.database.ContentObserver
import android.content.Context
import android.os.Handler
import android.util.Log
import android.media.AudioManager

class VolumeObserver(handler: Handler) : ContentObserver(handler) {
    private val TAG = "VolumeObserver"

    var previousVolume: Int = 0
    var context: Context? = null

    constructor(c: Context, handler: Handler) : this(handler) {
        context = c

        val audio = c.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    override fun deliverSelfNotifications(): Boolean {
        return super.deliverSelfNotifications()
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.d(TAG, "Settings change detected")

        val audio = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC)

        val delta = previousVolume - currentVolume
        if (delta > 0) {
            Log.i(TAG,"Decreased: " + currentVolume)
        } else if (delta < 0) {
            Log.i(TAG,"Increased: " + currentVolume)
        }
        previousVolume = currentVolume
        setExternalVolume(currentVolume)
    }

    private fun setExternalVolume(vol: Int) {
        Thread {
            MCApi.turnOn()
            MCApi.setVolume(vol)
        }.start()
    }
}