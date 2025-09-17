# Install Maldua Zimbra ActiveSync Extension

## Warning

**These are developer instructions.**

For Admin installation instructions please check [README.md](README.md) instead.

## Requisites

You have succesfully built zimbra-maldua-activesync using [EXTENSION_BUILD.md](EXTENSION_BUILD.md) instructions.

## Installation

Get `/opt/zimbra/conf/scripts/zimbra-maldua-activesync/extension/zetaactivesync.jar` from your build machine and copy it to your production machine on `/tmp/zetaactivesync.jar` .

This needs to be run as the root user:

```
mkdir /opt/zimbra/lib/ext/zetaactivesync
chmod 775 /opt/zimbra/lib/ext/zetaactivesync
cp /tmp/zetaactivesync.jar /opt/zimbra/lib/ext/zetaactivesync/zetaactivesync.jar
chmod 444 /opt/zimbra/lib/ext/zetaactivesync/zetaactivesync.jar
```

## Zimbra Mailbox restart

For the new Extension to be used the Zimbra Mailbox has to be restarted.

```
sudo su - zimbra -c 'zmmailboxdctl restart'
```
