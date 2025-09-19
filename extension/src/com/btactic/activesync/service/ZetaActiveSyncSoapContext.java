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
import javax.xml.namespace.QName;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.service.DocumentHandler;
import com.zimbra.soap.ZimbraSoapContext;
import com.zimbra.soap.SoapProtocol;

/**
 * A wrapper for ZimbraSoapContext that forces getPort() to return the account's MTA auth port.
 */
public class ZetaActiveSyncSoapContext extends ZimbraSoapContext {

    private final int forcedPort;

    /**
     * Wrap an existing ZimbraSoapContext and force getPort() to return forcedPort.
     *
     * @param orig The original ZimbraSoapContext to wrap
     * @param forcedPort The port to return from getPort()
     * @throws ServiceException
     */
    public ZetaActiveSyncSoapContext(ZimbraSoapContext orig, int forcedPort) throws ServiceException {
        super(
            orig.getContextElement(),
            orig.getRequestQName(),
            (DocumentHandler) orig.getHandler(),
            orig.getContextMap(),
            orig.getRequestProtocol()
        );
        this.forcedPort = forcedPort;
    }

    @Override
    public int getPort() {
        return forcedPort;
    }
}
