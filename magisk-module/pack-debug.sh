#!/bin/sh

mkdir -p output
cd debug && zip -r ../output/aw22xxx_leds_debug.zip ./* && cd ..
