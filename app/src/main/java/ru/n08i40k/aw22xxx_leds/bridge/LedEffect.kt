package ru.n08i40k.aw22xxx_leds.bridge

import java.io.File

data class LedEffect(
    val index: UByte,
    val name: String
) {
    companion object {
        fun read(file: File): List<LedEffect> = file
            .readText()
            .split("\n")
            .filter { it.startsWith("cfg") }
            .map {
                val index = Integer.parseInt(it.substring(4, it.indexOf(']')), 16).toUByte()
                val name = it.substring(it.indexOf("=") + 2)

                LedEffect(index, name)
            }
    }
}