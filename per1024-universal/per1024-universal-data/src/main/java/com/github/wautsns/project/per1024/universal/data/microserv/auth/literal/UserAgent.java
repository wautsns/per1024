package com.github.wautsns.project.per1024.universal.data.microserv.auth.literal;

import com.github.wautsns.apix.validator.core.Codable;
import com.github.wautsns.apix.validator.core.Extractor;
import com.github.wautsns.apix.validator.core.builtin.extractor.EEnum;
import com.github.wautsns.project.per1024.universal.data.common.Messages;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
public enum UserAgent implements Codable<String> {

    BROWSER, PC, ANDROID, IOS;

    private static final Extractor<String, UserAgent> EXTRACTOR = EEnum.ofCode(
        "400", Messages.REQUEST_IS_ILLEGAL, UserAgent.class);

    public static UserAgent extractFromHttpHeader(String userAgent) {
        return EXTRACTOR.extract(userAgent, BROWSER);
    }

    @Override
    public String getCode() {
        return name();
    }

}
