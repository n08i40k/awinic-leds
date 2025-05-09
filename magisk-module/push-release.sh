#!/bin/sh

APK_SOURCE_PATH=$1

rm -f ./output/aw22xxx_leds_release.zip

sh ./pack-debug.sh "$APK_SOURCE_PATH"

adb shell "rm -f /sdcard/aw22xxx_leds_release.zip"
adb push ./output/aw22xxx_leds_release.zip /sdcard/aw22xxx_leds_release.zip
adb shell "/data/adb/ksu/bin/ksud module install /sdcard/aw22xxx_leds_release.zip"
adb shell reboot