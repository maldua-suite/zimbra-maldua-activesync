# Build Maldua Zimbra ActiveSync Auth Extension

## Introduction

There are more traditional ways of building a Zimbra Extension. You might want to check those on [https://github.com/Zimbra/zm-extension-guide](https://github.com/Zimbra/zm-extension-guide).
This is my way which involves using an spare VPS where you install Zimbra 10.1.x.

## Requisites

- Ubuntu 20.04
- Zimbra 10.1.x installed

- ant (Does not matter too much if Distro JDK version that does not match Zimbra's JDK version.)

```
apt update
apt install ant git make zip
```

## Prepare build environment

```
sudo su - zimbra
mkdir -p /opt/zimbra/conf/scripts
cd /opt/zimbra/conf/scripts
git clone 'https://github.com/maldua-suite/zimbra-maldua-activesync-auth.git'

cd zimbra-maldua-activesync-auth/extension
ln -s /opt/zimbra/lib/jars lib
```

## Build

```
sudo su - zimbra

cd /opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension
ant jar
```

Sample build output:
```
Buildfile: /opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/build.xml

clean:
   [delete] Deleting directory /opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/build
   [delete] Deleting: /opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/zetaactivesyncauth.jar
    [mkdir] Created dir: /opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/build

compile:
    [javac] Compiling 48 source files to /opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/build

jar:
      [jar] Building jar: /opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/zetaactivesyncauth.jar

BUILD SUCCESSFUL
Total time: 2 seconds
```
.

## Jar

A new jar file should be found at:
```
/opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/zetaactivesyncauth.jar
```
.
