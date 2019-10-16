package com.github.wautsns.project.per1024.universal.data.common;

import lombok.experimental.UtilityClass;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 05, 2019
 */
@UtilityClass
public class Messages {

    @UtilityClass
    public static class Target {

        public final String CAPTCHA_SMS = "[CAPTCHA_SMS]";
        public final String EMAIL = "[EMAIL]";
        public final String PHONE = "[PHONE]";
        public final String CITY_ID = "[CITY.ID]";

    }

    public final String SERVICE_UNAVAILABLE = "[SERVICE_UNAVAILABLE]";

    public final String REQUEST_IS_ILLEGAL = "[REQUEST_IS_ILLEGAL]";

    public final String DOES_NOT_EXIST = "[DOES_NOT_EXIST]";
    public final String ALREADY_EXISTS = "[ALREADY_EXISTS]";

}
