package io.github.ellamosi.mchelper

class PowerController(private var mcDeviceState: MCDeviceState) {
    companion object {
        private const val TAG = "PowerController"
        private const val POWER_ON = "on"
        private const val MIN_POWER_CYCLE_DELAY = 10 * 1000
    }

    private var lastShutdown: Long? = null

    fun turnOn() {
        if (!hasRecentlyTurnedOff() && !isPowered()) MCApi.turnOn()
    }

    fun turnOff() {
        MCApi.turnOff()
        lastShutdown = System.currentTimeMillis()
    }

    private fun isPowered() : Boolean {
        return mcDeviceState.power() == POWER_ON
    }

    private fun hasRecentlyTurnedOff() : Boolean {
        return lastShutdown?.let {
            System.currentTimeMillis() - it < MIN_POWER_CYCLE_DELAY
        } ?: false
    }
}