package io.github.ellamosi.mchelper

class VolumeController {
    companion object Client {
        private const val TAG = "VolumeController"
    }

    fun adjustVolume(newVolume: Int) {
        MCApi.setVolume(newVolume)
    }
}