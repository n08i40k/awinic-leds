#!/system/bin/sh

PKG=ru.n08i40k.aw22xxx_leds
APP_UID=$(awk '/^'$PKG'/ {print $2}' /data/system/packages.list)

chown "$APP_UID".root /sys/class/leds/aw22xxx_led/hwen

chown "$APP_UID".root /sys/class/leds/aw22xxx_led/effect
chown "$APP_UID".root /sys/class/leds/aw22xxx_led/cfg

chown "$APP_UID".root /sys/class/leds/aw22xxx_led/frq
chown "$APP_UID".root /sys/class/leds/aw22xxx_led/rgb

chown "$APP_UID".root /sys/class/leds/aw22xxx_led/trigger