#!/bin/bash

function zmacl_disable () {
    if [[ -x /opt/zimbra/bin/zmacl ]]
    then
        /opt/zimbra/bin/zmacl disable
    fi
}

function zmacl_enable () {
    if [[ -x /opt/zimbra/bin/zmacl ]]
    then
        /opt/zimbra/bin/zmacl enable
    fi
}

function zeta_extension_install () {
    EXTENSION_ID="$1"
    EXTENSION_DIR="/opt/zimbra/lib/ext/${EXTENSION_ID}"

    if [ ! -d "${EXTENSION_DIR}" ] ; then
        mkdir -p "${EXTENSION_DIR}"
    fi
    cp ${EXTENSION_ID}.jar "${EXTENSION_DIR}"/${EXTENSION_ID}.jar
    chmod 444 "${EXTENSION_DIR}"/${EXTENSION_ID}.jar
}

function usage () {

cat << EOF

$0
Regular installation.

$0 --help
Print this help.

EOF

}

function restart_notice () {

cat << EOF

- Zimbra ActiveSync Auth Extension
- Zimbra ActiveSync Auth Admin zimlet
were installed.

Please restart mailboxd thanks to:

su - zimbra -c 'zmmailboxdctl restart'

so that this new extension is used.
EOF

}

if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit 1
fi

if [[ "$1" == '--help' ]]
then
    usage
    exit 0
fi

if [[ ! -x /opt/zimbra/bin/zmmailboxdctl ]]
  then echo "Please run on a mailbox node."
  exit 1
fi

zmacl_disable

zeta_extension_install "zetaactivesyncauth"

su - zimbra -c 'zmzimletctl -l deploy '"$(pwd)"'/com_btactic_activesyncauth_admin.zip'

zmacl_enable

restart_notice
