package io.github.ellamosi.mchelper.actions

import io.github.ellamosi.mchelper.InputController
import io.github.ellamosi.mchelper.PowerController
import io.github.ellamosi.mchelper.VolumeController

abstract class MCHAction {
    abstract fun execute(power: PowerController, input: InputController, volume: VolumeController)
}