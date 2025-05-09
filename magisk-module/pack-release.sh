#!/bin/sh

APK_SOURCE_PATH=$1

mkdir -p output

cd release || exit

mkdir -p ./system/priv-app/AwinicLeds/

cp "../$APK_SOURCE_PATH" ./system/priv-app/AwinicLeds/AwinicLeds.apk || exit

zip -r ../output/aw22xxx_leds_release.zip ./* || exit

cd ..