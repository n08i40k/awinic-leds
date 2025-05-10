#!/system/bin/sh

if [ ! -f "/sys/class/leds/aw22xxx_led/hwen" ]; then
  echo No led files found! Exitting...
  exit 1
fi