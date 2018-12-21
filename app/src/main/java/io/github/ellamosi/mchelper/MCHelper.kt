package io.github.ellamosi.mchelper

import android.app.Application

class MCHelper: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MCHelper
            private set
    }
}