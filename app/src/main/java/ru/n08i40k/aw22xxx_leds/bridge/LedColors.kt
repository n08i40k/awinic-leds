package ru.n08i40k.aw22xxx_leds.bridge

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.io.File

object LedColors {
    fun readColor(hex: String): Color {
        val raw = Integer.parseUnsignedInt(hex, 16).toUInt()

        val b = (raw and 0x00FF0000u) shr 16
        val r = (raw and 0x0000FF00u) shr 8
        val g = (raw and 0x000000FFu)

        return Color(((0xFFu shl 24) or (r shl 16) or (g shl 8) or b).toInt())
    }

    fun read(file: File): List<Color> = file
        .readText()
        .split("\n")
        .filter { it.isNotBlank() }
        .map { readColor(it.substring(11)) }

    fun write(file: File, index: UByte, color: Color) {
        val argb = color.toArgb().toUInt()

        val r = (argb and 0x00FF0000u) shr 16
        val g = (argb and 0x0000FF00u) shr 8
        val b = (argb and 0x000000FFu)

        file.writeText("0x${index} 0x${((b shl 16) or (r shl 8) or g).toString(16)}")
    }
}