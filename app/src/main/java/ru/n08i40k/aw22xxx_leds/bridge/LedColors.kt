package ru.n08i40k.aw22xxx_leds.bridge

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.io.File

object LedColors {
    fun read(file: File): List<Color> = file
        .readText()
        .split("\n")
        .filter { it.isNotBlank() }
        .map { Color(Integer.parseInt(it.substring(11), 16) or 0xFF000000.toInt()) }

    fun write(file: File, index: UByte, color: Color) =
        file.writeText("0x${index} 0x${(color.toArgb().toUInt() and 0xFFFFFFu).toString(16)}")
}