package io.github.ellamosi.mchelper

import android.util.Log
import java.net.URL
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.math.log10

class MCApi {
    companion object Client {
        private val TAG = "MCApi"
        private val BASE_URL = "http://192.168.1.11/YamahaExtendedControl/v1/"
        private val TV_INPUT = "optical2"
        private val POWER_ON = "on"
        private val MIN_VOL = 0
        private val MAX_VOL = 161

        fun turnOn() {
            URL(BASE_URL + "main/setPower?power=on").readText()
        }

        fun turnOff() {
            URL(BASE_URL + "main/setPower?power=standby").readText()
        }

        fun setTvInput() {
            URL(BASE_URL + "main/setInput?input=" + TV_INPUT).readText()
        }

        fun getStatus() : JSONObject {
            val stringResponse = URL(BASE_URL + "main/getStatus").readText()
            return JSONTokener(stringResponse).nextValue() as JSONObject
        }

        fun setInputOnVolume(vol : Int) {
            val status = getStatus()
            val input = status.getString("input")
            val power = status.getString("power")
            if (input != TV_INPUT) setTvInput()
            setVolume(vol)
            if (power != POWER_ON) turnOn()
        }

        fun isOnTvInput() : Boolean {
            val status = getStatus()
            val input = status.getString("input")
            return this.TV_INPUT == input
        }

        fun setVolume(vol: Int) {
            var targetVol = if (vol == 0) 0 else (log10(vol.toDouble()) * 80.0).toInt() + 1
            Log.d(TAG, "targetVol: " + targetVol)
            URL(BASE_URL + "main/setVolume?volume=" + targetVol).readText()
        }
    }
}