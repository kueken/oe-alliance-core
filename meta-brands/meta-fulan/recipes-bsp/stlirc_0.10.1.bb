DESCRIPTION = "LIRC is a package that allows you to decode and send infra-red signals of many commonly used remote controls."
DESCRIPTION_append_lirc = " This package contains the lirc daemon, libraries and tools."
DESCRIPTION_append_lirc-exec = " This package contains a daemon that runs programs on IR signals."
DESCRIPTION_append_lirc-remotes = " This package contains some config files for remotes."
DESCRIPTION_append_lirc-nslu2example = " This package contains a working config for RC5 remotes and a modified NSLU2."
HOMEPAGE = "http://www.lirc.org"
SECTION = "console/network"
LICENSE = "GPLv2"
DEPENDS = "virtual/kernel libxslt-native alsa-lib libftdi libusb1 libusb-compat jack portaudio-v19 python3-pyyaml python3-setuptools-native"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RCONFLICTS_${PN} = "lirc"
RREPLACES_${PN} = "lirc"

PR = "r8"

SRC_URI = "https://downloads.sourceforge.net/project/lirc/LIRC/0.10.1/lirc-${PV}.tar.bz2 \
    file://0001-Fix-build-on-32bit-arches-with-64bit-time_t.patch \
    file://fix_build_errors.patch \
    file://lircd.service \
    file://lircd.init \
    file://lircd_amiko8900.conf \
    file://lircd_amikoalien.conf \
    file://lircd_spark.conf \
    file://lircexec.init \
    file://lirc_options.conf \
    file://lirc.tmpfiles \
"
SRC_URI[md5sum] = "86c3f8e4efaba10571addb8313d1e040"
SRC_URI[sha256sum] = "8b753c60df2a7f5dcda2db72c38e448ca300c3b4f6000c1501fcb0bd5df414f2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/lirc-${PV}"

SYSTEMD_PACKAGES = "lirc lirc-exec"
SYSTEMD_SERVICE_${PN} = "lircd.service lircmd.service lircd-setup.service lircd-uinput.service"
SYSTEMD_SERVICE_${PN}-exec = "irexec.service"
SYSTEMD_AUTO_ENABLE_lirc = "enable"
SYSTEMD_AUTO_ENABLE_lirc-exec = "enable"

inherit autotools pkgconfig systemd python3native distutils-common-base

PACKAGECONFIG[systemd] = "--with-systemdsystemunitdir=${systemd_unitdir}/system/,--without-systemdsystemunitdir,systemd"

PACKAGECONFIG ?= " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', ' systemd', '', d)} \
"
CACHED_CONFIGUREVARS = "HAVE_WORKING_POLL=yes"

#EXTRA_OEMAKE = 'SUBDIRS="lib daemons tools"'
# Ensure python-pkg/VERSION exists
do_configure_append() {
    cp ${S}/VERSION ${S}/python-pkg/
}

# Create PYTHON_TARBALL which LIRC needs for install-nodist_pkgdataDATA
do_install_prepend() {
    rm -rf ${WORKDIR}/lirc-${PV}/python-pkg/dist/
    mkdir ${WORKDIR}/lirc-${PV}/python-pkg/dist/
    tar --exclude='${WORKDIR}/lirc-${PV}/python-pkg/*' -czf ${WORKDIR}/lirc-${PV}/python-pkg/dist/lirc-${PV}.tar.gz ${S}
}

# In code, path to python is a variable that is replaced with path to native version of it
# during the configure stage, e.g ../recipe-sysroot-native/usr/bin/python3-native/python3.
# Replace it with #!/usr/bin/env python3
do_install_append() {
    sed -i '1c#!/usr/bin/env python' ${D}${bindir}/lirc-setup \
                                      ${D}${PYTHON_SITEPACKAGES_DIR}/lirc-setup/lirc-setup \
                                      ${D}${bindir}/irtext2udp \
                                      ${D}${bindir}/lirc-init-db \
                                      ${D}${bindir}/irdb-get \
                                      ${D}${bindir}/pronto2lirc \
                                      ${D}${sbindir}/lircd-setup
    install -m 0755 -d ${D}${sysconfdir}
    install -m 0755 -d ${D}${sysconfdir}/lirc
    install -m 0755 -d ${D}${systemd_unitdir}/system
    install -m 0755 -d ${D}${libdir}/tmpfiles.d
    if [ -e ${WORKDIR}/lircd_${MACHINEBUILD}.conf ]; then
       install -m 0644 ${WORKDIR}/lircd_${MACHINEBUILD}.conf ${D}${sysconfdir}/lirc/
    elif [ "${BRAND_OEM}" = "fulan" ]; then
       install -m 0644 ${WORKDIR}/lircd_spark.conf ${D}${sysconfdir}/lirc/
    fi
    install -m 0644 ${WORKDIR}/lirc_options.conf ${D}${sysconfdir}/lirc/
    install -m 0644 ${WORKDIR}/lircd.service ${D}${systemd_unitdir}/system/
    install -m 0755 ${WORKDIR}/lircexec.init ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/lirc.tmpfiles ${D}${libdir}/tmpfiles.d/lirc.conf
    rm -rf ${D}${libdir}/lirc/plugins/*.la
    rmdir ${D}/var/run/lirc ${D}/var/run
    chown -R root:root ${D}${datadir}/lirc/contrib
}

PACKAGES =+ "${PN}-contrib ${PN}-exec ${PN}-plugins ${PN}-python"

RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-exec = "${PN}"
RDEPENDS_${PN}-python = "python"

RRECOMMENDS_${PN} = "${PN}-exec ${PN}-plugins"

FILES_${PN}-plugins = "${libdir}/lirc/plugins/*.so ${datadir}/lirc/configs"
FILES_${PN}-contrib = "${datadir}/lirc/contrib"
FILES_${PN}-exec = "${bindir}/irexec ${sysconfdir}/lircexec ${systemd_unitdir}/system/irexec.service"
FILES_${PN} += "${systemd_unitdir}/system/lircexec.init"
FILES_${PN} += "${systemd_unitdir}/system/lircd.service"
FILES_${PN} += "${systemd_unitdir}/system/lircd.socket"
FILES_${PN} += "${libdir}/tmpfiles.d/lirc.conf"
FILES_${PN}-dbg += "${libdir}/lirc/plugins/.debug"
FILES_${PN}-python += "${libdir}/python*/site-packages"


INITSCRIPT_PACKAGES = "lirc lirc-exec"
INITSCRIPT_NAME_lirc-exec = "lircexec"
INITSCRIPT_PARAMS_lirc-exec = "defaults 21"

# this is for distributions that don't use udev
pkg_postinst_${PN}_append() {
    if [ ! -c $D/dev/lirc -a ! -f /sbin/udevd ]; then mknod $D/dev/lirc c 61 0; fi
}

SECURITY_CFLAGS = "${SECURITY_NO_PIE_CFLAGS}"
