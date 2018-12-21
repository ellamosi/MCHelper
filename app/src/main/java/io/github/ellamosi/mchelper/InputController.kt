package io.github.ellamosi.mchelper

class InputController(private var mcDeviceState: MCDeviceState) {
    companion object {
        private const val TAG = "InputController"
        private const val TV_INPUT = "optical2"
    }

    fun setInput() {
        if (!isOnTvInput()) MCApi.setInput(TV_INPUT)
    }

    fun isOnTvInput() : Boolean {
        return TV_INPUT == mcDeviceState.input()
    }
}