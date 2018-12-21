package io.github.ellamosi.mchelper

import io.github.ellamosi.mchelper.actions.MCHAction
import java.util.concurrent.*

class MCHWorker : Runnable {
    companion object {
        private const val TAG = "MCHWorker"
    }

    private val mcDeviceState = MCDeviceState()
    private val powerController = PowerController(mcDeviceState)
    private val inputController = InputController(mcDeviceState)
    private val volumeController = VolumeController()

    private val requestQueue = LinkedBlockingQueue<MCHAction>()

    override fun run() {
        try {
            while(true) {
                requestQueue.take().execute(powerController, inputController, volumeController)
            }
        } catch (e: InterruptedException) {}
    }

    fun enqueueAction(event: MCHAction) {
        requestQueue.put(event)
    }
}