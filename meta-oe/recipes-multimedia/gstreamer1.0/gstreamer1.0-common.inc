GST_VERSION_FULL = "1.18.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "29a8099d1d4bd8717c13923e710e92e67e335353"
SRCREV_gst_libav = "097313530cae4a49437a779a9ded0ade8113c26b"

SRCREV_gst_plugins_bad = "e5c3c106a2da607953fea36e3a253b382c939684"
SRCREV_gst_plugins_base = "4013b8003e78971dd01b055066c12f8aaadb8897"
SRCREV_gst_plugins_good = "7c44cdb0e00dd1c9932d8e5194b09fcf4e1e6fc1"
SRCREV_gst_plugins_ugly = "720672eed30b3be47b2f26d67554786c0d3693ad"

S = "${WORKDIR}/git"
