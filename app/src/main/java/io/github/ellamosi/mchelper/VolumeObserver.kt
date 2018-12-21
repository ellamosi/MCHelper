package io.github.ellamosi.mchelper

import android.content.Context
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Handler
import io.github.ellamosi.mchelper.actions.AdjustVolume

class VolumeObserver(private val worker: MCHWorker, handler: Handler) : ContentObserver(handler) {
    companion object {
        private const val TAG = "VolumeObserver"
    }

    private var previousVolume: Int = 0
    private var audioManager: AudioManager

    init {
        val context = MCHelper.instance.applicationContext
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)

        val newVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val delta = previousVolume - newVolume
        val volumeChanged = delta != 0
        if (volumeChanged) worker.enqueueAction(AdjustVolume(newVolume))
    }
}