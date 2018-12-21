package io.github.ellamosi.mchelper

import android.util.Log
import java.net.URL
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.math.log10

class MCApi {
    companion object Client {
        private const val TAG = "MCApi"
        private const val BASE_URL = "http://192.168.1.11/YamahaExtendedControl/v1/"
        private const val MIN_VOL = 0
        private const val MAX_VOL = 161

        fun turnOn() {
            URL(BASE_URL + "main/setPower?power=on").readText()
        }

        fun turnOff() {
            URL(BASE_URL + "main/setPower?power=standby").readText()
        }

        fun setInput(input: String) {
            URL(BASE_URL + "main/setInput?input=" + input).readText()
        }

        fun getStatus() : JSONObject {
            val stringResponse = URL(BASE_URL + "main/getStatus").readText()
            return JSONTokener(stringResponse).nextValue() as JSONObject
        }

        fun setVolume(vol: Int) {
            val targetVol = if (vol == 0) 0 else (log10(vol.toDouble()) * 80.0).toInt() + 1
            Log.d(TAG, "targetVol: $targetVol")
            URL(BASE_URL + "main/setVolume?volume=" + targetVol).readText()
        }
    }
}