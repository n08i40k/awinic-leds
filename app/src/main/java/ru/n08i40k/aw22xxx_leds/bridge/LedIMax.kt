package ru.n08i40k.aw22xxx_leds.bridge

import java.io.File

object LedIMax {
    @Suppress("unused")
    enum class Values {
        IMax2mA,
        IMax3mA,
        IMax4mA,
        IMax6mA,
        IMax9mA,
        IMax10mA,
        IMax15mA,
        IMax20mA,
        IMax30mA,
        IMax40mA,
        IMax45mA,
        IMax60mA,
        IMax75mA,
        Unknown
    }

    fun read(file: File): Values = file
        .readText()
        .split("\n")
        .last { it.isNotBlank() }
        .let {
            val index = Integer.parseInt(it.substring(15, 17), 16)

            Values.entries.getOrElse(index) { Values.Unknown }
        }
}