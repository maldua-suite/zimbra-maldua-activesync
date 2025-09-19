/*
 * Maldua Zimbra ActiveSync Extension
 * Copyright (C) 2025 BTACTIC, S.C.C.L.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.btactic.activesync.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Server;
import com.zimbra.cs.server.ServerConfig;

/**
 * Central whitelist store for ZetaActiveSyncAuth.
 * Populated internally by the extension. Includes loopback addresses,
 * all mailbox server IPs, and HTTP throttle safe IPs.
 */
public class ZetaActiveSyncWhitelist {

    private static final ZetaActiveSyncWhitelist INSTANCE = new ZetaActiveSyncWhitelist();
    private final Set<String> whitelist = Collections.synchronizedSet(new HashSet<>());

    private ZetaActiveSyncWhitelist() {
        // Adapted from store/src/java/com/zimbra/cs/servlet/DoSFilter.java
        // add loopback addresses by default
        addWhitelistAddress("127.0.0.1");
        addWhitelistAddress("::1");
        addWhitelistAddress("0:0:0:0:0:0:0:1");

        // add mailbox server IPs and throttle safe IPs
        try {
            List<Server> servers = Provisioning.getInstance().getAllServers(Provisioning.SERVICE_MAILBOX);
            for (Server server : servers) {
                try {
                    InetAddress[] addresses = InetAddress.getAllByName(server.getServiceHostname());
                    for (InetAddress address : addresses) {
                        addWhitelistAddress(address.getHostAddress());
                    }
                } catch (UnknownHostException e) {
                    ZimbraLog.misc.warn("Invalid hostname: " + server.getServiceHostname(), e);
                }
            }

            String[] addrs = ServerConfig.getAddrListCsv(
                    Provisioning.getInstance().getLocalServer().getHttpThrottleSafeIPs());
            for (String addr : addrs) {
                addWhitelistAddress(addr);
            }
        } catch (ServiceException e) {
            ZimbraLog.misc.warn("Unable to get throttle safe IPs", e);
        }
    }

    public static ZetaActiveSyncWhitelist getInstance() {
        return INSTANCE;
    }

    /**
     * Add an IP or CIDR entry into the whitelist.
     * Private: only internal methods can call this.
     */
    private boolean addWhitelistAddress(String cidrOrIp) {
        String trimmed = cidrOrIp.trim();
        boolean added = whitelist.add(trimmed);
        if (added) {
            ZimbraLog.misc.debug("[ZetaActiveSyncWhitelist] added address [%s]", trimmed);
        }
        return added;
    }

    /**
     * Return a copy of all whitelist entries.
     */
    public Set<String> getAll() {
        return new HashSet<>(whitelist);
    }
}
