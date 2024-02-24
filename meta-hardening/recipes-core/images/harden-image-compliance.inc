include conf/distro/include/cve-extra-exclusions.inc

set_sshd_config_perms () {
    #!/bin/sh -e
    if [ "${@bb.utils.contains('IMAGE_FEATURES', 'ssh-server-openssh', 'yes', 'no', d)}" = "yes" ]; then
        chmod 0600 ${IMAGE_ROOTFS}/etc/ssh/sshd_config*
    fi
}

ROOTFS_POSTPROCESS_COMMAND_append = " set_sshd_config_perms;"
