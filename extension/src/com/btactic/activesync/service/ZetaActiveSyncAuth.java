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

import com.zimbra.common.service.ServiceException;

import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.Element;

import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.service.account.Auth;
import com.zimbra.soap.SoapEngine;
import com.zimbra.soap.ZimbraSoapContext;

import java.util.Map;

public class ZetaActiveSyncAuth {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        // Construct a "full" AuthRequest with all possible elements
        Element authRequest = new Element.XMLElement("AuthRequest");

        // Copy account and 'by' attribute
        Element accountEl = request.getOptionalElement(AccountConstants.E_ACCOUNT);
        if (accountEl != null) {
            authRequest.addElement(AccountConstants.E_ACCOUNT)
                       .addAttribute(AccountConstants.A_BY, accountEl.getAttribute(AccountConstants.A_BY, "name"))
                       .setText(accountEl.getText());
        }

        // Copy password / recoveryCode / preauth / authToken / jwtToken / deviceId / 2FA
        copyOptional(request, authRequest, AccountConstants.E_PASSWORD);
        copyOptional(request, authRequest, AccountConstants.E_RECOVERY_CODE);
        copyOptional(request, authRequest, AccountConstants.E_PREAUTH);
        copyOptional(request, authRequest, AccountConstants.E_AUTH_TOKEN);
        copyOptional(request, authRequest, "jwtToken");
        copyOptional(request, authRequest, AccountConstants.E_DEVICE_ID);
        copyOptional(request, authRequest, AccountConstants.E_TWO_FACTOR_CODE);
        copyOptional(request, authRequest, AccountConstants.A_GENERATE_DEVICE_ID);
        copyOptional(request, authRequest, AccountConstants.A_TRUSTED_DEVICE);

        // Copy prefs / attrs / requestedSkin
        copyOptional(request, authRequest, AccountConstants.E_PREFS);
        copyOptional(request, authRequest, AccountConstants.E_ATTRS);
        copyOptional(request, authRequest, AccountConstants.E_REQUESTED_SKIN);

        // Delegate to actual Auth handler
        return new Auth().handle(authRequest, context);
    }

    private void copyOptional(Element source, Element target, String elementName) throws ServiceException {
        Element child = source.getOptionalElement(elementName);
        if (child != null) {
            Element newChild = target.addElement(elementName).setText(child.getText());
            for (String attrName : child.getAttributeNames()) {
                newChild.addAttribute(attrName, child.getAttribute(attrName));
            }
        }
    }

}
