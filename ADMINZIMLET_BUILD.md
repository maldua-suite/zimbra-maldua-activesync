# Build Maldua Zimbra ActiveSync Auth Admin Zimlet

## Introduction

This is quite straight-forward to do.

## Requisites

- zip
- git

```
apt update
apt install zip git
```

## Prepare build environment

```
cd /tmp
git clone 'https://github.com/maldua-suite/zimbra-maldua-activesync-auth.git'
```

## Build

```
cd /tmp/zimbra-maldua-activesync-auth/adminZimlet/com_btactic_activesyncauth_admin/
zip --quiet -r ../com_btactic_activesyncauth_admin.zip *
```

## Zip

A new zip file should be found at:
```
/tmp/zimbra-maldua-activesync-auth/adminZimlet/com_btactic_activesyncauth_admin.zip
```
.
