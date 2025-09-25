# Install Maldua Zimbra ActiveSync Auth Extension

## Warning

**These are developer instructions.**

For Admin installation instructions please check [README.md](README.md) instead.

## Requisites

You have succesfully built zimbra-maldua-activesync-auth using [EXTENSION_BUILD.md](EXTENSION_BUILD.md) instructions.

## Installation

Get `/opt/zimbra/conf/scripts/zimbra-maldua-activesync-auth/extension/zetaactivesyncauth.jar` from your build machine and copy it to your production machine on `/tmp/zetaactivesyncauth.jar` .

This needs to be run as the root user:

```
mkdir /opt/zimbra/lib/ext/zetaactivesyncauth
chmod 775 /opt/zimbra/lib/ext/zetaactivesyncauth
cp /tmp/zetaactivesyncauth.jar /opt/zimbra/lib/ext/zetaactivesyncauth/zetaactivesyncauth.jar
chmod 444 /opt/zimbra/lib/ext/zetaactivesyncauth/zetaactivesyncauth.jar
```

## Zimbra Mailbox restart

For the new Extension to be used the Zimbra Mailbox has to be restarted.

```
sudo su - zimbra -c 'zmmailboxdctl restart'
```
