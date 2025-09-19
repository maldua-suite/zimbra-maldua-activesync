# Release Maldua Zimbra ActiveSync Extension

## Introduction

There are more traditional ways of building a Zimbra Extension. You might want to check those on [https://github.com/Zimbra/zm-extension-guide](https://github.com/Zimbra/zm-extension-guide).
This is my way which involves using an spare VPS where you install Zimbra 10.1.x.

## Requisites

- Ubuntu 20.04
- Zimbra 10.1.x installed

- ant (Does not matter too much if Distro JDK version that does not match Zimbra's JDK version.)
- zip
- git
- sed

```
apt update
apt install ant git make zip sed
```

## Prepare release environment

```
sudo su - zimbra
mkdir -p /opt/zimbra/conf/scripts
cd /opt/zimbra/conf/scripts
git clone 'https://github.com/maldua-suite/zimbra-maldua-activesync.git'

cd zimbra-maldua-activesync/extension
ln -s /opt/zimbra/lib/jars lib
```

## Release

```
sudo su - zimbra

cd /opt/zimbra/conf/scripts/zimbra-maldua-activesync/
./release.sh
```

## tar.gz

A new tar.gz file should be found at:
```
/opt/zimbra/conf/scripts/zimbra-maldua-activesync/release/zimbra-maldua-activesync_0.1.0.tar.gz
```
where 0.1.0 is the version.
.
