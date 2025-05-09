package ru.n08i40k.aw22xxx_leds.ui.model

import android.content.Context
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import ru.n08i40k.aw22xxx_leds.bridge.LedEffect
import ru.n08i40k.aw22xxx_leds.bridge.LedIMax
import ru.n08i40k.aw22xxx_leds.bridge.SysFsBridge
import ru.n08i40k.aw22xxx_leds.proto.settings

data class LedsUiState(
    val enabled: Boolean,

    val currentIMax: LedIMax.Values,

    val currentEffect: Int,
    val availableEffect: List<LedEffect>,

    val frequency: Int,

    val colors: List<Color>,

    val useOwnValues: Boolean,
    val useSavedSettings: Boolean
) {
    companion object {
        fun load(context: Context): LedsUiState {
            val data = context.settings.data

            return LedsUiState(
                enabled = SysFsBridge.IO.enabled,
                currentIMax = SysFsBridge.IO.currentIMax,
                currentEffect = SysFsBridge.IO.currentEffect.toInt(),
                availableEffect = SysFsBridge.IO.availableEffects,
                frequency = SysFsBridge.IO.frequency,
                colors = SysFsBridge.IO.colors,
                useOwnValues = runBlocking { data.map { it.useOwnValues }.first() },
                useSavedSettings = runBlocking { data.map { it.useSavedSettings }.first() }
            )
        }
    }
}