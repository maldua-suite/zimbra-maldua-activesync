#!/bin/bash

VERSION="$(head -n 1 VERSION)"
ZIP_DIR="zimbra-maldua-activesync-auth_${VERSION}"
VERSION_TEMPLATE="9.99.999"

# Build extension
cd extension
ant jar
cd ..

# Build admin zimlet
cd adminZimlet/com_btactic_activesyncauth_admin
sed -i 's/'"${VERSION_TEMPLATE}"'/'"${VERSION}"'/g' com_btactic_activesyncauth_admin.xml
zip --quiet -r ../com_btactic_activesyncauth_admin.zip *
cd ../..

# Zip directory
mkdir release/${ZIP_DIR}
cp extension/zetaactivesyncauth.jar release/${ZIP_DIR}/zetaactivesyncauth.jar

cp adminZimlet/com_btactic_activesyncauth_admin.zip release/${ZIP_DIR}/com_btactic_activesyncauth_admin.zip

cp install.sh release/${ZIP_DIR}/install.sh

# Zip file
cd release
tar czf ${ZIP_DIR}.tar.gz ${ZIP_DIR}
cd ..
