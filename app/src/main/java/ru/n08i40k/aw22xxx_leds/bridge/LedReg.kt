package ru.n08i40k.aw22xxx_leds.bridge

import java.io.File

data class LedReg(
    val index: UByte,
    val value: UByte
) {
    companion object {
        fun read(file: File): List<LedReg> = file
            .readText()
            .split("\n")
            .filter { it.isNotBlank() }
            .map {
                val index = Integer.parseInt(it.substring(6, 8), 16).toUByte()
                val value = Integer.parseInt(it.substring(11, 13), 16).toUByte()

                LedReg(index, value)
            }
    }
}