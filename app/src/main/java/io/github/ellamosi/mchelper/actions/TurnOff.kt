package io.github.ellamosi.mchelper.actions

import io.github.ellamosi.mchelper.*

class TurnOff : MCHAction() {
    override fun execute(power: PowerController, input: InputController, volume: VolumeController) {
        if (input.isOnTvInput()) power.turnOff()
    }
}