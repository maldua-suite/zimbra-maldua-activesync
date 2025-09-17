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

import org.dom4j.Namespace;
import org.dom4j.QName;

public final class ZetaActiveSyncConstants {

    public static final String USER_SERVICE_URI = "/service/soap/";

    public static final String NAMESPACE_STR = "urn:zimbraAccount";
    public static final Namespace NAMESPACE = Namespace.get(NAMESPACE_STR);

    public static final String E_ZETA_ACTIVESYNC_AUTH_REQUEST = "ZetaActiveSyncAuthRequest";
    public static final QName ZETA_ACTIVESYNC_AUTH_REQUEST = QName.get(E_ZETA_ACTIVESYNC_AUTH_REQUEST, NAMESPACE);

    public static final String E_ZETA_ACTIVESYNC_AUTH_RESPONSE = "ZetaActiveSyncAuthResponse";
    public static final QName ZETA_ACTIVESYNC_AUTH_RESPONSE = QName.get(E_ZETA_ACTIVESYNC_AUTH_RESPONSE, NAMESPACE);

}
