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

import com.zimbra.cs.account.AccountServiceException;
import com.google.common.base.Strings;

/**
 * Exception used for ActiveSync authentication failures.
 */
@SuppressWarnings("serial")
public class ActiveSyncAuthException extends AccountServiceException {

    public static final String ACTIVESYNC_DISABLED = "account.ACTIVESYNC_DISABLED";
    public static final String NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS = "account.NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS";

    private final String mReason;
    private final String mAcctName;

    private ActiveSyncAuthException(String acctName, String namePassedIn,
                                    String reason, String code,
                                    boolean isReceiversFault, Throwable cause) {
        super(buildMessage(code, namePassedIn), code, isReceiversFault, cause);
        this.mReason = reason;
        this.mAcctName = acctName;
    }

    private static String buildMessage(String code, String namePassedIn) {
        if (ACTIVESYNC_DISABLED.equals(code)) {
            return "ActiveSync disabled for [" + Strings.nullToEmpty(namePassedIn) + "]";
        } else if (NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS.equals(code)) {
            return "Request not allowed for [" + Strings.nullToEmpty(namePassedIn) + "] (not in zimbraHttpThrottleSafeIPs)";
        }
        return "ActiveSync auth error for [" + Strings.nullToEmpty(namePassedIn) + "]";
    }

    public String getReason() {
        return mReason == null ? "" : mReason;
    }

    public String getAcctName() {
        return mAcctName;
    }

    public static ActiveSyncAuthException ACTIVESYNC_DISABLED(String acctName, String namePassedIn) {
        return ACTIVESYNC_DISABLED(acctName, namePassedIn, null, null);
    }

    public static ActiveSyncAuthException ACTIVESYNC_DISABLED(String acctName, String namePassedIn, String reason) {
        return ACTIVESYNC_DISABLED(acctName, namePassedIn, reason, null);
    }

    public static ActiveSyncAuthException ACTIVESYNC_DISABLED(String acctName, String namePassedIn, String reason, Throwable t) {
        return new ActiveSyncAuthException(acctName, namePassedIn, reason, ACTIVESYNC_DISABLED, SENDERS_FAULT, t);
    }

    public static ActiveSyncAuthException ACTIVESYNC_DISABLED(String namePassedIn, String reason, Throwable t) {
        return new ActiveSyncAuthException("N/A", namePassedIn, reason, ACTIVESYNC_DISABLED, SENDERS_FAULT, t);
    }

    public static ActiveSyncAuthException ACTIVESYNC_DISABLED(String reason, Throwable t) {
        return new ActiveSyncAuthException("N/A", "", reason, ACTIVESYNC_DISABLED, SENDERS_FAULT, t);
    }

    public static ActiveSyncAuthException ACTIVESYNC_DISABLED(String reason) {
        return new ActiveSyncAuthException("N/A", "", reason, ACTIVESYNC_DISABLED, SENDERS_FAULT, null);
    }

    public static ActiveSyncAuthException NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(String acctName, String namePassedIn) {
        return NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(acctName, namePassedIn, null, null);
    }

    public static ActiveSyncAuthException NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(String acctName, String namePassedIn, String reason) {
        return NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(acctName, namePassedIn, reason, null);
    }

    public static ActiveSyncAuthException NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(String acctName, String namePassedIn, String reason, Throwable t) {
        return new ActiveSyncAuthException(acctName, namePassedIn, reason, NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS, SENDERS_FAULT, t);
    }

    public static ActiveSyncAuthException NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(String namePassedIn, String reason, Throwable t) {
        return new ActiveSyncAuthException("N/A", namePassedIn, reason, NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS, SENDERS_FAULT, t);
    }

    public static ActiveSyncAuthException NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(String reason, Throwable t) {
        return new ActiveSyncAuthException("N/A", "", reason, NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS, SENDERS_FAULT, t);
    }

    public static ActiveSyncAuthException NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS(String reason) {
        return new ActiveSyncAuthException("N/A", "", reason, NOT_IN_ZIMBRAHTTPTHROTTLESAFEIPS, SENDERS_FAULT, null);
    }
}
