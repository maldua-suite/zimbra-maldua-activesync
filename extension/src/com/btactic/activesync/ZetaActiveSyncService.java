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

import com.zimbra.cs.service.account.AccountService;

import com.zimbra.soap.DocumentDispatcher;
import com.zimbra.soap.DocumentService;

import com.btactic.activesync.service.ZetaActiveSyncAuth;
import com.btactic.activesync.service.ZetaActiveSyncConstants;

public class ZetaActiveSyncService extends AccountService implements DocumentService {

    public void registerHandlers(DocumentDispatcher dispatcher) {
        dispatcher.registerHandler(ZetaActiveSyncConstants.ZETA_ACTIVESYNC_AUTH_REQUEST, new ZetaActiveSyncAuth());
    }

}
