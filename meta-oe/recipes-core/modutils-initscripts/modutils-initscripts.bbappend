SRC_URI_append_sh4 += "\
    file://spark_modutils.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-ignore_modversions.patch \
           "
INITSCRIPT_NAME = "modutils.sh"
INITSCRIPT_PARAMS = "start 04 S ."
