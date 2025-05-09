package ru.n08i40k.aw22xxx_leds.bridge

import java.io.File

object LedTrigger {
    fun read(file: File): Pair<List<String>, Int> {
        var selectedIndex = 0

        val triggers = file
            .readText()
            .split(" ")
            .mapIndexed { index, trigger ->
                if (trigger[0] == '[') {
                    selectedIndex = index
                    return@mapIndexed trigger.substring(1, trigger.length - 1)
                }

                trigger
            }

        return triggers to selectedIndex
    }

    fun write(file: File, trigger: String) = file.writeText(trigger)
}