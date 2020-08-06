GST_VERSION_FULL = "1.17.2.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "e243e152f06d376c429c40b017e3e1e13bc09af6"
SRCREV_gst_libav = "8d3c0a1f8a8fa6411c3a4f50a06cd077118e7883"

SRCREV_gst_plugins_bad = "e70ec38000404de1c871d835a6e35dbfcc47884d"
SRCREV_gst_plugins_base = "dd5f7f1bf97eaec3d967b3fe670e3e600088904a"
SRCREV_gst_plugins_good = "28a616f6933f11cab372cf4e1bb143b99c89bd0c"
SRCREV_gst_plugins_ugly = "995a135df6276a060ce16078fd6db54668163199"

S = "${WORKDIR}/git"
