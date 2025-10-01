/*
 * ***** BEGIN LICENSE BLOCK *****
 * Maldua Zimbra ActiveSync Auth Extension
 * (C) 2025 BTACTIC, S.C.C.L.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>. *****
 * END LICENSE BLOCK *****
 */

package com.btactic.activesync.service.exception;

import com.google.common.base.Strings;

import com.zimbra.cs.account.AccountServiceException;

/**
 * Exception thrown when ActiveSync access is disabled for an account.
 */
@SuppressWarnings("serial")
public class ActiveSyncDisabledException extends AccountServiceException {

    public static final String ACTIVESYNC_DISABLED = "account.ACTIVESYNC_DISABLED";

    private final String mReason;
    private final String mAcctName;

    private ActiveSyncDisabledException(String acctName, String namePassedIn,
                                        String reason, boolean isReceiversFault, Throwable cause) {
        super("ActiveSync disabled for [" + Strings.nullToEmpty(namePassedIn) + "]",
              ACTIVESYNC_DISABLED, isReceiversFault, cause);
        this.mReason = reason;
        this.mAcctName = acctName;
    }

    public String getReason() {
        return mReason == null ? "" : mReason;
    }

    public String getAcctName() {
        return mAcctName;
    }

    public static ActiveSyncDisabledException ACTIVESYNC_DISABLED(String acctName, String namePassedIn) {
        return ACTIVESYNC_DISABLED(acctName, namePassedIn, null, null);
    }

    public static ActiveSyncDisabledException ACTIVESYNC_DISABLED(String acctName, String namePassedIn, String reason) {
        return ACTIVESYNC_DISABLED(acctName, namePassedIn, reason, null);
    }

    public static ActiveSyncDisabledException ACTIVESYNC_DISABLED(String acctName, String namePassedIn, String reason, Throwable t) {
        return new ActiveSyncDisabledException(acctName, namePassedIn, reason, SENDERS_FAULT, t);
    }

    public static ActiveSyncDisabledException ACTIVESYNC_DISABLED(String namePassedIn, String reason, Throwable t) {
        return new ActiveSyncDisabledException("N/A", namePassedIn, reason, SENDERS_FAULT, t);
    }

    public static ActiveSyncDisabledException ACTIVESYNC_DISABLED(String reason, Throwable t) {
        return new ActiveSyncDisabledException("N/A", "", reason, SENDERS_FAULT, t);
    }

    public static ActiveSyncDisabledException ACTIVESYNC_DISABLED(String reason) {
        return new ActiveSyncDisabledException("N/A", "", reason, SENDERS_FAULT, null);
    }
}
