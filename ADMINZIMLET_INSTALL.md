# Install Maldua Zimbra ActiveSync Auth Admin Zimlet

## Warning

**These are developer instructions.**

For Admin installation instructions please check [README.md](README.md) instead.

## Requisites

You have succesfully built zimbra-maldua-activesync-auth admin zimlet using [ADMINZIMLET_BUILD.md](ADMINZIMLET_BUILD.md) instructions.

## Installation

**Note**: This procedure has to be done on all of your mailboxes.

Get `/tmp/zimbra-maldua-activesync-auth/adminZimlet/com_btactic_activesyncauth_admin.zip` from your build machine and copy it to your production machine on `/tmp/com_btactic_activesyncauth_admin.zip` .

This needs to be run as the root user:

```
chown zimbra:zimbra /tmp/com_btactic_activesyncauth_admin.zip
```

And then:
```
sudo su - zimbra
zmzimletctl deploy /tmp/com_btactic_activesyncauth_admin.zip
```
.

## Zimbra Mailbox restart

For the new admin Zimlet to be used the Zimbra Mailbox needs to be restarted.

```
sudo su - zimbra -c 'zmmailboxdctl restart'
```
