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
package com.btactic.activesync;

import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.ZimbraLog;

import com.zimbra.cs.account.auth.AuthContext.Protocol;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Server;

import com.zimbra.cs.service.account.AccountService;
import com.zimbra.cs.service.account.Auth;

import com.zimbra.soap.DocumentDispatcher;
import com.zimbra.soap.DocumentHandler;
import com.zimbra.soap.DocumentService;
import com.zimbra.soap.SoapEngine;

import com.btactic.activesync.service.ZetaActiveSyncConstants;

public class ZetaActiveSyncService extends AccountService implements DocumentService {

    public void registerHandlers(DocumentDispatcher dispatcher) {
        // We cannot override the protocol or port inside ZetaActiveSyncAuth (a subclass of Auth) because:
        // 1. By the time Auth.handle() is called, the ZimbraSoapContext may already have been instantiated
        //    elsewhere in the request processing, locking in the port and protocol values.
        // 2. Auth reads the port and protocol from the context map only when constructing ZimbraSoapContext.
        // 3. Therefore, setting the port/protocol inside the subclass is too late - the context has already been consumed.
        // 4. Wrapping the call in a dispatcher-level handler allows us to inject the correct values before Auth sees them.
        dispatcher.registerHandler(
            ZetaActiveSyncConstants.ZETA_ACTIVESYNC_AUTH_REQUEST,
            new DocumentHandler() {
                @Override
                public Element handle(Element request, Map<String, Object> context) throws ServiceException {
                    // 1) Set protocol in context
                    context.put("proto", Protocol.zsync);

                    // 2) Compute MTA auth port from local server
                    int mtaAuthPort = 7073; // default fallback
                    try {
                        Server localServer = Provisioning.getInstance().getLocalServer();
                        if (localServer != null) {
                            mtaAuthPort = localServer.getIntAttr(Provisioning.A_zimbraMtaAuthPort, 7073);
                        }
                    } catch (Exception e) {
                        ZimbraLog.extensions.warn("[ZetaActiveSync] Failed to get local server MTA auth port, using 7073", e);
                    }

                    // 3) Override REQUEST_PORT in context
                    ZimbraLog.extensions.info("[ZetaActiveSync] Overriding REQUEST_PORT with {" + mtaAuthPort + "}");
                    context.put(SoapEngine.REQUEST_PORT, mtaAuthPort);

                    // 4) Delegate to standard Auth handler
                    return new Auth().handle(request, context);
                }

                @Override
                public boolean needsAuth(Map<String, Object> context) {
                    return false;
                }

            }
        );
    }

}
