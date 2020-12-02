GST_VERSION_FULL = "1.18.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "12937488ae9a4a122ac361eafa90d8ac7233b5ed"
SRCREV_gst_libav = "b16d1e804fcf077fc7bddffa256fa585400e44f9"

SRCREV_gst_plugins_bad = "2f2e8a93e653eefa4ea7f6e1239e0952c002ff58"
SRCREV_gst_plugins_base = "d88fedb5553cf55a020e711fb72f3f1f4574745e"
SRCREV_gst_plugins_good = "5392f244d9a6b8ce0e2a74b6fc7add014740831c"
SRCREV_gst_plugins_ugly = "720672eed30b3be47b2f26d67554786c0d3693ad"

S = "${WORKDIR}/git"
