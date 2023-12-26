package io.github.ellamosi.mchelper

import java.net.URL
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.math.log10

class MCApi {
    companion object Client {
        private const val BASE_URL = "http://192.168.1.11/YamahaExtendedControl/v1/main/"

        fun turnOn() {
            getRequest("setPower?power=on")
        }

        fun turnOff() {
            getRequest("setPower?power=standby")
        }

        fun setInput(input: String) {
            getRequest("setInput?input=$input")
        }

        fun getStatus() : JSONObject {
            val stringResponse = getRequest("getStatus")
            return JSONTokener(stringResponse).nextValue() as JSONObject
        }

        fun setVolume(vol: Int) {
            val targetVol = if (vol == 0) 0 else (log10(vol.toDouble()) * 80.0).toInt() + 1
            getRequest("setVolume?volume=$targetVol")
        }

        private fun getRequest(args: String) : String {
            return URL(BASE_URL + args).readText()
        }
    }
}