package com.github.wautsns.project.per1024.universal.data.microserv.auth.literal;

import com.github.wautsns.apix.validator.annotation.assist.BuiltInAttributeMetadata;
import com.github.wautsns.apix.validator.annotation.builtin.enumeration.VCodeOfEnum;
import com.github.wautsns.apix.validator.core.Codable;
import com.github.wautsns.apix.validator.core.Extractor;
import com.github.wautsns.apix.validator.core.builtin.extractor.EEnum;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;

import lombok.Getter;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
public enum LoginMode implements Codable<String> {

    PWD, SMS, OPEN;

    private static final Extractor<String, LoginMode> EXTRACTOR_FOR_CODE = EEnum.ofCode(
        "400", VCodeOfEnum.DEFAULT_MESSAGE, LoginMode.class)
        .mutateWith(BuiltInAttributeMetadata.TARGET.getName(), MessagesOfAuth.Target.LOGIN_MODE);

    public static LoginMode extractFromCode(String code){
        return EXTRACTOR_FOR_CODE.extract(code);
    }

    @Getter
    private final String code;

    private LoginMode() {
        this.code = name().toLowerCase();
    }

}
