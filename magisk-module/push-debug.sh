#!/bin/sh

rm -f ./output/aw22xxx_leds_debug.zip

sh ./pack-debug.sh

adb shell "rm -f /sdcard/aw22xxx_leds_debug.zip"
adb push ./output/aw22xxx_leds_debug.zip /sdcard/aw22xxx_leds_debug.zip
adb shell "/data/adb/ksu/bin/ksud module install /sdcard/aw22xxx_leds_debug.zip"
adb shell reboot