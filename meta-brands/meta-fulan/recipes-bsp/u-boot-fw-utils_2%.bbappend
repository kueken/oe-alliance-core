FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/${MACHINE}:"

# PRINC := "${@int(PRINC) + 1}"

SRC_URI += " \
     file://fw_env.config \
     file://fw_env.config-7162 \
     file://fw_printenv.sh \
"

do_install_append() {
        install -d ${D}${sysconfdir}
        install -d ${D}/lib/
        install -m 0644 ${WORKDIR}/fw_env.config  ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/fw_env.config-7162 ${D}${sysconfdir}
        mv ${D}${base_sbindir}/fw_printenv ${D}/lib
        rm ${D}${base_sbindir}/fw_setenv
        install -m 0755 ${WORKDIR}/fw_printenv.sh ${D}${base_sbindir}/fw_printenv
        ln -s fw_printenv ${D}${base_sbindir}/fw_setenv
        ln -s fw_printenv ${D}/lib/fw_setenv
}

FILES_${PN} += " \
        /lib \
        ${sysconfdir}/fw_env.config-7162 \
"
