package io.github.ellamosi.mchelper.actions

import io.github.ellamosi.mchelper.*

class AdjustVolume(private val newVolume: Int) : MCHAction() {
    override fun execute(power: PowerController, input: InputController, volume: VolumeController) {
        volume.adjustVolume(newVolume)
        power.turnOn()
        input.setInput()
    }
}