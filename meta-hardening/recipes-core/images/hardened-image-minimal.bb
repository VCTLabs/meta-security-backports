SUMMARY = "A small image for an example hardening OE."

IMAGE_INSTALL = "packagegroup-core-boot packagegroup-hardening"
IMAGE_INSTALL_append = " os-release"

IMAGE_FEATURES = ""
IMAGE_LINGUAS = " "

LICENSE = "MIT"

IMAGE_ROOTFS_SIZE ?= "8192"

inherit core-image extrausers
#IMAGE_CLASSES_append = " extrausers"

# plain text input => temppwd
# generated with openssl passwd
DIGEST_PASSWD ?= "\$1\$mXvzp4dT\$JUw5RNZODE9wkxXmTk0Mf."

ROOT_DEFAULT_PASSWORD ?= "${DIGEST_PASSWD}"
DEFAULT_ADMIN_ACCOUNT ?= "admin1"
DEFAULT_ADMIN_GROUP ?= "wheel"
DEFAULT_ADMIN_ACCOUNT_PASSWORD ?= "${DIGEST_PASSWD}"

EXTRA_USERS_PARAMS = "${@bb.utils.contains('DISABLE_ROOT', 'True', "usermod -L root;", "usermod -p '${ROOT_DEFAULT_PASSWORD}' root;", d)}"

EXTRA_USERS_PARAMS_append = " useradd  ${DEFAULT_ADMIN_ACCOUNT};"
EXTRA_USERS_PARAMS_append = " groupadd  ${DEFAULT_ADMIN_GROUP};"
EXTRA_USERS_PARAMS_append = " usermod -p '${DEFAULT_ADMIN_ACCOUNT_PASSWORD}' ${DEFAULT_ADMIN_ACCOUNT};"
EXTRA_USERS_PARAMS_append = " usermod -aG ${DEFAULT_ADMIN_GROUP}  ${DEFAULT_ADMIN_ACCOUNT};"
