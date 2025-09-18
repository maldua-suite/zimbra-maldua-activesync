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

import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.ZimbraLog;

import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Domain;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.auth.AuthContext.Protocol;

import com.zimbra.cs.service.account.Auth;

import com.zimbra.soap.SoapEngine;
import com.zimbra.soap.ZimbraSoapContext;

public final class ZetaActiveSyncAuth extends Auth {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraLog.extensions.info("[ZetaActiveSync] forcing proto=zsync");
        context.put("proto", Protocol.zsync);

        Account acct = null;
        Integer mtaAuthPort = null;

        try {
            Provisioning prov = Provisioning.getInstance();

            // Replicate the account resolution logic from Auth.handle()
            Element acctEl = request.getOptionalElement(AccountConstants.E_ACCOUNT);
            if (acctEl != null) {
                String acctValuePassedIn = acctEl.getText();
                String acctValue = acctValuePassedIn;
                String acctByStr = acctEl.getAttribute(
                    AccountConstants.A_BY,
                    AccountBy.name.name());
                AccountBy acctBy = AccountBy.fromString(acctByStr);

                if (acctBy == AccountBy.name) {
                    Element virtualHostEl = request.getOptionalElement(AccountConstants.E_VIRTUAL_HOST);
                    String virtualHost = virtualHostEl == null ? null : virtualHostEl.getText().toLowerCase();
                    if (virtualHost != null && acctValue.indexOf('@') == -1) {
                        Domain d = prov.get(Key.DomainBy.virtualHostname, virtualHost);
                        if (d != null) {
                            acctValue = acctValue + "@" + d.getName();
                        }
                    }
                }

                acct = prov.get(acctBy, acctValue);
            }
        } catch (Exception e) {
            ZimbraLog.extensions.warn("[ZetaActiveSync] Could not resolve account from request", e);
        }

        if (acct != null) {
            mtaAuthPort = acct.getServer().getMtaAuthPort();
            ZimbraLog.extensions.debug(
                "[ZetaActiveSync] Overriding REQUEST_PORT with account's MTA auth port {}", mtaAuthPort);
        } else {
            try {
                mtaAuthPort = Provisioning.getInstance().getLocalServer().getMtaAuthPort();
                ZimbraLog.extensions.debug(
                    "[ZetaActiveSync] Account is null, falling back to local server MTA auth port {}", mtaAuthPort);
            } catch (Exception e) {
                ZimbraLog.extensions.warn("[ZetaActiveSync] Could not determine local server MTA auth port", e);
            }
        }

        if (mtaAuthPort != null) {
            context.put(SoapEngine.REQUEST_PORT, mtaAuthPort);
        } else {
            ZimbraLog.extensions.debug("[ZetaActiveSync] mtaAuthPort is null, not overriding REQUEST_PORT");
        }

        return super.handle(request, context);
    }

}
