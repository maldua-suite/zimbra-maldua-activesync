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
package com.btactic.activesync.service;

import java.util.Map;

import com.zimbra.cs.service.account.Auth;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.soap.SoapEngine;
import com.zimbra.soap.ZimbraSoapContext;

/**
 * Wraps Auth to inject port and protocol overrides before delegating.
 */
public class ZetaActiveSyncAuth extends Auth {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        // use zsc.getProtocol() to create a new Element with the same QName as the original request
        Element newRequest = Element.create(zsc.getProtocol(), request.getQName());

        // Copy E_ACCOUNT if present
        Element acctEl = request.getOptionalElement(AccountConstants.E_ACCOUNT);
        if (acctEl != null) {
            Element newAcctEl = newRequest.addElement(AccountConstants.E_ACCOUNT)
                                         .setText(acctEl.getText());
            String byAttr = acctEl.getAttribute(AccountConstants.A_BY, null);
            if (byAttr != null) {
                newAcctEl.addAttribute(AccountConstants.A_BY, byAttr);
            }
            Element virtualHostEl = acctEl.getOptionalElement(AccountConstants.E_VIRTUAL_HOST);
            if (virtualHostEl != null) {
                newAcctEl.addElement(AccountConstants.E_VIRTUAL_HOST)
                         .setText(virtualHostEl.getText());
            }
        }

        // Copy password
        String password = request.getAttribute(AccountConstants.E_PASSWORD, null);
        if (password != null) {
            newRequest.addAttribute(AccountConstants.E_PASSWORD, password);
        }

        // Copy recovery code if present
        String recoveryCode = request.getAttribute(AccountConstants.E_RECOVERY_CODE, null);
        if (recoveryCode != null) {
            newRequest.addAttribute(AccountConstants.E_RECOVERY_CODE, recoveryCode);
        }

        // Copy generateDeviceId flag
        boolean generateDeviceId = request.getAttributeBool(AccountConstants.A_GENERATE_DEVICE_ID, false);
        newRequest.addAttribute(AccountConstants.A_GENERATE_DEVICE_ID, generateDeviceId);

        // Copy deviceId
        String deviceId = request.getAttribute(AccountConstants.E_DEVICE_ID, null);
        if (deviceId != null) {
            newRequest.addAttribute(AccountConstants.E_DEVICE_ID, deviceId);
        }

        // Copy optional authToken / jwtToken
        Element authTokenEl = request.getOptionalElement(AccountConstants.E_AUTH_TOKEN);
        if (authTokenEl != null) {
            newRequest.addElement(AccountConstants.E_AUTH_TOKEN).setText(authTokenEl.getText());
        }
        Element jwtTokenEl = request.getOptionalElement(AccountConstants.E_JWT_TOKEN);
        if (jwtTokenEl != null) {
            newRequest.addElement(AccountConstants.E_JWT_TOKEN).setText(jwtTokenEl.getText());
        }

        // Copy optional PREAUTH
        Element preAuthEl = request.getOptionalElement(AccountConstants.E_PREAUTH);
        if (preAuthEl != null) {
            Element newPreAuth = newRequest.addElement(AccountConstants.E_PREAUTH)
                                          .setText(preAuthEl.getText());
            newPreAuth.addAttribute(AccountConstants.A_TIMESTAMP, preAuthEl.getAttributeLong(AccountConstants.A_TIMESTAMP, 0));
            newPreAuth.addAttribute(AccountConstants.A_EXPIRES, preAuthEl.getAttributeLong(AccountConstants.A_EXPIRES, 0));
        }

        // Copy PREFS / ATTRS / REQUESTED_SKIN elements if present
        Element prefsEl = request.getOptionalElement(AccountConstants.E_PREFS);
        if (prefsEl != null) {
            newRequest.addElement(AccountConstants.E_PREFS);
        }
        Element attrsEl = request.getOptionalElement(AccountConstants.E_ATTRS);
        if (attrsEl != null) {
            newRequest.addElement(AccountConstants.E_ATTRS);
        }
        Element requestedSkinEl = request.getOptionalElement(AccountConstants.E_REQUESTED_SKIN);
        if (requestedSkinEl != null) {
            newRequest.addElement(AccountConstants.E_REQUESTED_SKIN).setText(requestedSkinEl.getText());
        }

        // Delegate to Auth
        return super.handle(newRequest, context);
    }
}
