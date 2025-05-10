#!/system/bin/sh

enable_logging() {
  mkdir -p /tmp/aw22xxx_leds

  # Make FIFO
  LOG_FIFO="/tmp/aw22xxx_leds/$1.fifo"
  mkfifo "$LOG_FIFO"

  # Remove FIFO after exit from script
  trap 'rm -f "$LOG_FIFO"' EXIT

  # Redirect FIFO output to file and stdout
  tee "/tmp/aw22xxx_leds/$1.log" < "$LOG_FIFO" &

  # Redirect stdout and stderr to FIFO
  exec > "$LOG_FIFO" 2>&1
}