/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */

package com.btactic.activesync.service;

import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;

import com.zimbra.soap.ZimbraSoapContext;

import com.zimbra.cs.account.auth.AuthContext.Protocol;
import com.zimbra.cs.service.account.Auth;

public final class ZetaActiveSyncAuth extends Auth {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraLog.extensions.info("[ZetaActiveSync] forcing proto=zsync");
        context.put("proto", Protocol.zsync);

        Account acct = null;
        int mtaAuthPort;

        try {
            // Try to resolve the account from the request
            String by = request.getAttribute(AccountConstants.A_BY, null);
            String key = request.getAttribute(AccountConstants.A_ACCOUNT, null);
            if (by != null && key != null) {
                acct = Provisioning.getInstance().get(AccountBy.fromString(by), key);
            }
        } catch (Exception e) {
            ZimbraLog.extensions.warn("[ZetaActiveSync] Could not resolve account from request", e);
        }

        if (acct != null) {
            mtaAuthPort = acct.getServer().getMtaAuthPort();
            ZimbraLog.extensions.debug(
                "[ZetaActiveSync] Overriding REQUEST_PORT with account's MTA auth port {}", mtaAuthPort);
        } else {
            mtaAuthPort = Provisioning.getInstance().getLocalServer().getMtaAuthPort();
            ZimbraLog.extensions.debug(
                "[ZetaActiveSync] Account is null, falling back to local server MTA auth port {}", mtaAuthPort);
        }

        // Override the port in context so ZimbraSoapContext sees SMTP port
        context.put(SoapEngine.REQUEST_PORT, mtaAuthPort);

        return super.handle(request, context);
    }

}
