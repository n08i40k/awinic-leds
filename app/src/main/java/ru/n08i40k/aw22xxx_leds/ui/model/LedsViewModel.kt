package ru.n08i40k.aw22xxx_leds.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.n08i40k.aw22xxx_leds.Application
import ru.n08i40k.aw22xxx_leds.SavedLedSettings
import ru.n08i40k.aw22xxx_leds.bridge.SysFsBridge
import ru.n08i40k.aw22xxx_leds.proto.settings

class LedsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LedsUiState.load(Application.INSTANCE))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                _state.emit(LedsUiState.load(Application.INSTANCE))
                delay(1_000L)
            }
        }
    }

    suspend fun update() {
        _state.emit(LedsUiState.load(Application.INSTANCE))

        if (_state.value.useSavedSettings) {
            Application.INSTANCE.settings.updateData {
                it.toBuilder()
                    .setLed(
                        SavedLedSettings
                            .newBuilder()
                            .setHwen(_state.value.enabled)
                            .setEffect(_state.value.currentEffect)
                            .setFrq(_state.value.frequency)
                            .putAllRgb(
                                _state.value.colors
                                    .mapIndexed { index, color -> index to color.toArgb() }
                                    .toMap()
                            )
                            .build()
                    )
                    .build()
            }
        }
    }

    fun setEnabled(enabled: Boolean) {
        SysFsBridge.IO.enabled = enabled

        runBlocking { update() }
    }

    fun setUseSavedSettings(save: Boolean) {
        runBlocking {
            Application.INSTANCE.settings.updateData {
                it.toBuilder().setUseSavedSettings(save).build()
            }

            update()
        }
    }

    fun setUseOwnValues(use: Boolean) {
        runBlocking {
            Application.INSTANCE.settings.updateData { it.toBuilder().setUseOwnValues(use).build() }
            SysFsBridge.flushCfg(use)

            update()
        }
    }

    fun setEffect(index: UByte) {
        SysFsBridge.IO.currentEffect = index
        SysFsBridge.flushCfg(_state.value.useOwnValues)

        runBlocking { update() }
    }

    fun setFrequency(frequency: Int) {
        SysFsBridge.IO.frequency = frequency
        SysFsBridge.flushCfg(_state.value.useOwnValues)

        runBlocking { update() }
    }

    fun setColor(index: UByte, color: Color) {
        SysFsBridge.IO.setColor(index, color)
        SysFsBridge.flushCfg(_state.value.useOwnValues)

        runBlocking { update() }
    }
}