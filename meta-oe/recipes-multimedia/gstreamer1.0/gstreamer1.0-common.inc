GST_VERSION_FULL = "1.18.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "877f2b68006e787dff2335282220764e400b4ec7"
SRCREV_gst_libav = "b16d1e804fcf077fc7bddffa256fa585400e44f9"

SRCREV_gst_plugins_bad = "0940d1b5b1df164a06a53a0fe8e7135a6b71ad79"
SRCREV_gst_plugins_base = "ee38a96c83661ab58a1a61196a6c6ae875393e28"
SRCREV_gst_plugins_good = "9d41fea6673e51af6846a7caaafcec5ebf07da8e"
SRCREV_gst_plugins_ugly = "2feb9715545775462cc6b6df60c9bdc45c4a50d4"

S = "${WORKDIR}/git"
