# Copyright (C) 2017 - 2023 Armin Kuster  <akuster808@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMARRY = "SCAP content for various platforms, upstream version"
HOME_URL = "https://www.open-scap.org/security-policies/scap-security-guide/"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9bfa86579213cb4c6adaffface6b2820"
LICENSE = "BSD-3-Clause"

# increment this for patch changes!!
PR = "r6"

SRCREV = "7fb44f78bf33232aad587e87a799afcf576f9b47"
SRC_URI = "git://github.com/ComplianceAsCode/content.git;branch=stable;protocol=https \
           file://0001-openembedded-profile-cleanup-add-expanded-profile.patch \
           file://0001-swap-bash-umask-rules-from-default-to-align-with-min.patch \
           file://0002-fix-rule-not-applicable-for-rules-checking-login-def.patch \
           file://0005-scap-security-guide-add-Petalinux-support.patch \
           file://0006-add-support-for-OE-product-variant-harden-distro-fro.patch \
           file://0001-openembedded-product-add-the-missing-table-transform.patch \
           file://run_eval.sh \
           "


DEPENDS = "openscap-native python3-pyyaml-native python3-jinja2-native libxml2-native expat-native coreutils-native"

S = "${WORKDIR}/git"
B = "${S}/build"

inherit cmake pkgconfig python3native

STAGING_OSCAP_BUILDDIR = "${TMPDIR}/work-shared/openscap/oscap-build-artifacts"
export OSCAP_CPE_PATH="${STAGING_OSCAP_BUILDDIR}${datadir_native}/openscap/cpe"
export OSCAP_SCHEMA_PATH="${STAGING_OSCAP_BUILDDIR}${datadir_native}/openscap/schemas"
export OSCAP_XSLT_PATH="${STAGING_OSCAP_BUILDDIR}${datadir_native}/openscap/xsl"

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += "-DENABLE_PYTHON_COVERAGE=OFF -DSSG_PRODUCT_DEFAULT=OFF -DSSG_PRODUCT_OPENEMBEDDED=ON"

do_configure[depends] += "openscap-native:do_install"

do_configure_prepend () {
    sed -i -e 's:NAMES\ sed:NAMES\ ${HOSTTOOLS_DIR}/sed:g' ${S}/CMakeLists.txt
    sed -i -e 's:NAMES\ grep:NAMES\ ${HOSTTOOLS_DIR}/grep:g' ${S}/CMakeLists.txt
}

do_install_append() {
    install -d ${D}${datadir}/openscap
    install  ${WORKDIR}/run_eval.sh ${D}${datadir}/openscap/.
}

FILES_${PN} += "${datadir}/xml ${datadir}/openscap"

RDEPENDS_${PN} = "openscap"

COMPATIBLE_HOST_libc-musl = "null"
