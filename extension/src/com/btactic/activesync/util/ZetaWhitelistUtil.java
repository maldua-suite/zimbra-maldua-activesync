/*
 * Maldua Zimbra ActiveSync Auth Extension
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
import java.util.Set;

/**
 * Utility to check if an IP is whitelisted (supports CIDR).
 */
public class ZetaWhitelistUtil {

    /**
     * Checks if the given IP (or comma-separated IP string) is whitelisted.
     * If the string contains multiple IPs separated by commas, only the last one is checked.
     */
    public static boolean isWhitelisted(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        String sanitizedIp = sanitizeIp(ip);

        Set<String> whitelist = ZetaActiveSyncWhitelist.getInstance().getAll();
        for (String entry : whitelist) {
            if (matches(sanitizedIp, entry)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sanitize the IP string.
     * If there are multiple IPs separated by comma, returns the last one trimmed.
     * Otherwise, returns the input trimmed.
     */
    public static String sanitizeIp(String ip) {
        String[] parts = ip.split(",");
        return parts[parts.length - 1].trim();
    }

    /**
     * Checks if the sanitized IP matches the whitelist entry (supports CIDR or exact match).
     */
    private static boolean matches(String ip, String cidrOrIp) {
        if (!cidrOrIp.contains("/")) {
            // Exact match case
            return ip.equals(cidrOrIp);
        }

        String[] parts = cidrOrIp.split("/");
        String baseIp = parts[0];
        int prefix;
        try {
            prefix = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        try {
            InetAddress target = InetAddress.getByName(ip);
            InetAddress base = InetAddress.getByName(baseIp);

            byte[] targetBytes = target.getAddress();
            byte[] baseBytes = base.getAddress();

            if (targetBytes.length != baseBytes.length) {
                return false; // IPv4 vs IPv6 mismatch
            }

            int fullBytes = prefix / 8;
            int remainingBits = prefix % 8;

            for (int i = 0; i < fullBytes; i++) {
                if (targetBytes[i] != baseBytes[i]) return false;
            }
            if (remainingBits > 0) {
                int mask = (0xFF << (8 - remainingBits)) & 0xFF;
                if ((targetBytes[fullBytes] & mask) != (baseBytes[fullBytes] & mask)) {
                    return false;
                }
            }
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
