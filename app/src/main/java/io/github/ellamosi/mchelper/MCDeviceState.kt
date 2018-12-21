package io.github.ellamosi.mchelper

import android.util.Log

class MCDeviceState {
    companion object {
        private const val TAG = "MCDeviceState"
        private const val MIN_REFRESH_DELAY = 10 * 1000
    }

    private var input: String? = null
    private var power: String? = null
    private var updatedAt: Long? = null

    private fun updateState() {
        Log.d(TAG, "State refresh")
        val status = MCApi.getStatus()
        input = status.getString("input")
        power = status.getString("power")
        updatedAt = System.currentTimeMillis()
    }

    fun input() : String {
        if (input == null || !isRecentlyUpdated()) updateState()
        return input!!
    }

    fun power() : String {
        if (power == null || !isRecentlyUpdated()) updateState()
        return power!!
    }

    private fun isRecentlyUpdated() : Boolean {
        return updatedAt?.let {
            System.currentTimeMillis() - it < MIN_REFRESH_DELAY
        } ?: false
    }
}