# Maldua's Zimbra ActiveSync

## About

**MALDUA'S Zimbra ActiveSync Extension & Administration Zimlet** brought to you by [BTACTIC, open source & cloud solutions](https://www.btactic.com).

ActiveSync is a mobile data synchronization app developed by Microsoft, originally released in 1996. It synchronizes data with handheld devices and desktop computers.

This ActiveSync helper will improve integration with [Z-Push](https://github.com/Z-Hub/Z-Push/) and [Z-Push Zimbra Backend](https://sourceforge.net/projects/zimbrabackend/).

Please notice that **it does not bring an equivalent functionality to an ActiveSync server on its own.**

---

**WARNING: The development stage is in ALPHA QUALITY and it is not ready for production deployment.**

---

## Supported Zimbra versions

- Zimbra 10.1.x

## Features

### 2FA Application Passcodes now work

Thanks to the new soap request page:

- Application Passcodes work as expected when 2FA is enabled for them.
- Non 2FA enabled accounts also login properly.

### zimbraHttpThrottleSafeIPs security filter

- Only allow connections from **zimbraHttpThrottleSafeIPs** ips by default for an increased security.

### Logins logging in audit.log

ActiveSync logins are now logged in audit.log file.

```
2025-09-19 19:40:17,304 INFO  [qtp1279309678-18:https://zimbra.example.net/service/soap/] [name=login@example.net;oip=1.2.3.4, 5.6.7.8;port=47834;ua=BlueMail 1.140.103 (20466)(...le2bw6) devip=1.2.3.4 ZPZB/74;soapId=56847ec2;] security - cmd=Auth; account=login@example.net; protocol=zsync;
```

## Licenses

### License (Extension)

```
Maldua Zimbra ActiveSync Extension
Copyright (C) 2025 BTACTIC, S.C.C.L.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see http://www.gnu.org/licenses/.
```

### License (Administration zimlet)

```
Maldua Zimbra ActiveSync Administration zimlet
Copyright (C) 2025 BTACTIC, S.C.C.L.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see http://www.gnu.org/licenses/.
```
