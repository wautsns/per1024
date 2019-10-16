package com.github.wautsns.project.per1024.universal.data.microserv.auth;

import lombok.experimental.UtilityClass;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 05, 2019
 */
@UtilityClass
public class MessagesOfAuth {

    @UtilityClass
    public static class Variables {

        public final String OPEN_PLATFORM = "open_platform";
        public final String OPEN_USER_INFO = "open_user_info";
        public final String USERNAME = "username";

    }

    @UtilityClass
    public static class Target {

        public final String RESOURCE_ID = "[RESOURCE_ID]";
        public final String RESOURCE_NAME = "[RESOURCE_NAME]";
        public final String RESOURCE_OPERATION = "[RESOURCE_OPERATION]";

        public final String LOGIN_MODE = "[LOGIN_MODE]";
        public final String OPEN_ID = "[OPEN_ID]";
        public final String OPEN_PLATFORM = "[OPEN_PLATFORM]";

        public final String UID = "[UID]";
        public final String USERNAME = "[USERNAME]";
        public final String PASSWORD = "[PASSWORD]";
        public final String USER_AUTHE_STATUS = "[USER_AUTHE_STATUS]";

    }

    public final String UNAUTHORIZED = "[UNAUTHORIZED]";
    public final String INSUFFICIENT_PERMISSION = "[INSUFFICIENT_PERMISSION]";
    public final String PERMISSION_IS_DISABLED = "[PERMISSION_IS_DISABLED]";
    public final String RESOURCE_IS_UNAVAILABLE = "[RESOURCE_IS_UNAVAILABLE]";

    public final String USER_IS_FROZEN = "[USER_IS_FROZEN]";
    public final String PASSWORD_IS_INCORRECT = "[PASSWORD_IS_INCORRECT]";
    public final String UNSUPPORTED_OPEN_PLATFORM = "[UNSUPPORTED_OPEN_PLATFORM]";
    public final String OPEN_ID_UNBOUND_USER = "[OPEN_ID_UNBOUND_USER]";

}
