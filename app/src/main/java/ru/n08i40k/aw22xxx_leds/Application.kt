package ru.n08i40k.aw22xxx_leds

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    companion object {
        private lateinit var _INSTANCE: ru.n08i40k.aw22xxx_leds.Application
        val INSTANCE get() = _INSTANCE
    }

    override fun onCreate() {
        _INSTANCE = this

        super.onCreate()
    }
}