package ru.n08i40k.aw22xxx_leds.bridge

import androidx.compose.ui.graphics.Color
import java.io.File
import kotlin.text.substring

@Suppress("unused")
object SysFsBridge {
    const val LED_DIR = "/sys/class/leds/aw22xxx_led"

    object Files {
        val directory = File(LED_DIR)

        val hwen = File("${LED_DIR}/hwen")
        val reg = File("${LED_DIR}/reg")

        val imax = File("${LED_DIR}/imax")

        // TODO: какие-то ёбнутые баги. я пока не буду это трогать.
//        val brightness = File("${LED_DIR}/brightness")
//        val max_brightness = File("${LED_DIR}/max_brightness")

        val effect = File("${LED_DIR}/effect")
        val cfg = File("${LED_DIR}/cfg")

        val frq = File("${LED_DIR}/frq")
        val rgb = File("${LED_DIR}/rgb")

        val trigger = File("${LED_DIR}/trigger")
    }

    object IO {
        var enabled: Boolean
            get() = Files.hwen.readText()[5] == '1'
            set(value) = Files.hwen.writeText(if (value) "1" else "0")

        val registers: List<LedReg>
            get() = LedReg.read(Files.reg)

        val currentIMax: LedIMax.Values
            get() = LedIMax.read(Files.imax)

        var currentEffect: UByte
            get() = Integer.parseInt(Files.effect.readText().substring(11, 13), 16).toUByte()
            set(index) = Files.effect.writeText(index.toString())

        val availableEffects: List<LedEffect>
            get() = LedEffect.read(Files.cfg)

        var frequency: Int
            get() = LedFrq.read(Files.frq)
            set(hz) = Files.frq.writeText(hz.toString())

        val colors: List<Color>
            get() = LedColors.read(Files.rgb)

        fun setColor(index: UByte, color: Color) = LedColors.write(Files.rgb, index, color)

        val triggers: Pair<List<String>, Int>
            get() = LedTrigger.read(Files.trigger)

        fun selectTrigger(trigger: String) = LedTrigger.write(Files.trigger, trigger)
    }

    val isPresent
        get() = try {
            File("/sys/class/leds/aw22xxx_led/").exists()
        } catch (_: Exception) {
            false
        }

    fun flushCfg(useOwnValues: Boolean) {
        // https://github.com/MiCode/Xiaomi_Kernel_OpenSource/blob/ffbfcd5d72d0c8b84512a613f05c4c7fa9faffb6/drivers/leds/leds-aw22xxx.c#L410
        Files.cfg.writeText(if (useOwnValues) "2" else "1")
    }
}