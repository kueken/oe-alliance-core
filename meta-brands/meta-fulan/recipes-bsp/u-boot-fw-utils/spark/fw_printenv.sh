#!/bin/sh
# wrapper script to find the correct config file
read a b BOARD < /proc/cpuinfo
if [ "$BOARD" = hdk7105 ]; then
	exec /lib/${0##*/} --config=/etc/fw_env.config-7162 $@
	exit $? # should never happern :-)
fi
exec /lib/${0##*/} $@
