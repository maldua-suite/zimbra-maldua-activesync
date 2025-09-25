#!/bin/bash

VERSION="$(head -n 1 VERSION)"
ZIP_DIR="zimbra-maldua-activesync-auth_${VERSION}"
VERSION_TEMPLATE="9.99.999"

# Build extension
cd extension
ant jar
cd ..

# Zip directory
mkdir release/${ZIP_DIR}
cp extension/zetaactivesyncauth.jar release/${ZIP_DIR}/zetaactivesyncauth.jar

cp install.sh release/${ZIP_DIR}/install.sh

# Zip file
cd release
tar czf ${ZIP_DIR}.tar.gz ${ZIP_DIR}
cd ..
