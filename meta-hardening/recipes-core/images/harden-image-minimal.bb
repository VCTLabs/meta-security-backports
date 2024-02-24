SUMMARY = "A small image for an example of hardening OE."

require harden-image-compliance.inc

IMAGE_INSTALL = "packagegroup-core-boot packagegroup-hardening"
IMAGE_INSTALL_append = " os-release"

IMAGE_FEATURES = ""
IMAGE_LINGUAS = " "

LICENSE = "MIT"

IMAGE_ROOTFS_SIZE ?= "8192"

inherit core-image extrausers

# user params no longer accept a plain-text passwd, so:
#  openssl passwd -6 temppwd
# note the root user is disabled by default
ROOT_DEFAULT_PASSWORD ?= "\$6\$u4Ssckj5S7Yhfgtr\$83iD2HcVLyrLpVQhj8K2rv7WY9qcqOReU0Vv94QwkvBFUOAMgMYKJAj4TdJV/MlqRcGBP0y1hYCyDUf.sSxo10"
DEFAULT_ADMIN_ACCOUNT ?= "myadmin"
DEFAULT_ADMIN_GROUP ?= "wheel"
DEFAULT_ADMIN_ACCOUNT_PASSWORD ?= "\$6\$u4Ssckj5S7Yhfgtr\$83iD2HcVLyrLpVQhj8K2rv7WY9qcqOReU0Vv94QwkvBFUOAMgMYKJAj4TdJV/MlqRcGBP0y1hYCyDUf.sSxo10"

EXTRA_USERS_PARAMS = "${@bb.utils.contains('DISABLE_ROOT', 'True', "usermod -L root;", "usermod -P '${ROOT_DEFAULT_PASSWORD}' root;", d)}"

EXTRA_USERS_PARAMS:append = " useradd  ${DEFAULT_ADMIN_ACCOUNT};" 
EXTRA_USERS_PARAMS:append = " groupadd  ${DEFAULT_ADMIN_GROUP};" 
EXTRA_USERS_PARAMS:append = " usermod -p '${DEFAULT_ADMIN_ACCOUNT_PASSWORD}' ${DEFAULT_ADMIN_ACCOUNT};" 
EXTRA_USERS_PARAMS:append = " usermod -aG ${DEFAULT_ADMIN_GROUP} ${DEFAULT_ADMIN_ACCOUNT};" 
