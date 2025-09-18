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

import com.zimbra.common.util.ZimbraLog;

public final class ZetaActiveSyncAuth extends Auth {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraLog.extensions.info("[ZetaActiveSync] " + "1");
        // Force protocol to be zsync so that Application Passcodes are accepted for 2FA
        context.put("proto", Protocol.zsync);
        ZimbraLog.extensions.info("[ZetaActiveSync] " + "2");

        Element superElement = super.handle(request, context);
        ZimbraLog.extensions.info("[ZetaActiveSync] " + "3");

        return superElement;
    }

}
