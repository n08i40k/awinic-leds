#!/system/bin/sh

resetprop -w sys.boot_completed 0

PKG=ru.n08i40k.aw22xxx_leds
APP_UID=$(awk '/^'$PKG'/ {print $2}' /data/system/packages.list)

set_mode() {
  # set sepolicy context
  chcon u:object_r:sysfs_led:s0 /sys/class/leds/aw22xxx_led/"$1"
  
  # set app uid as owner
  chown "$APP_UID".root /sys/class/leds/aw22xxx_led/"$1"
  
  # set required mode
  chmod "$2" /sys/class/leds/aw22xxx_led/"$1"
}

set_mode hwen 644
set_mode effect 644
set_mode cfg 644
set_mode frq 644
set_mode rgb 644
set_mode trigger 644

# prevent value from change
set_mode reg 444
set_mode imax 444
set_mode brightness 444
set_mode max_brightness 444
set_mode task0 444
set_mode task1 444