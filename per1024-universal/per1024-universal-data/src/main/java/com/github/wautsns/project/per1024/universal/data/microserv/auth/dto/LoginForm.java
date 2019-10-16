package com.github.wautsns.project.per1024.universal.data.microserv.auth.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.apix.validator.core.builtin.criterion.CText;
import com.github.wautsns.project.per1024.universal.data.common.Messages;
import com.github.wautsns.project.per1024.universal.data.common.validator.VCaptchaSMS;
import com.github.wautsns.project.per1024.universal.data.common.validator.VEmail;
import com.github.wautsns.project.per1024.universal.data.common.validator.VPhone;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.LoginMode;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.SpecificLoginMode;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VPassword;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VUsername;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@Data
@Accessors(chain = true)
@JsonNaming(SnakeCaseStrategy.class)
public class LoginForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mode;
    private String identifier;
    private String certification;

    public LoginForm setCode(String code) {
        this.certification = code;
        return this;
    }

    public SpecificLoginMode checkAndGetSpecificLoginMode() {
        switch (LoginMode.extractFromCode(mode)) {
        case PWD:
            VPassword.VALIDATOR.validate(certification);
            if (identifier == null || identifier.isEmpty()) {
                // 此处必定抛出 ApiX(标识符为空, 直接提示用户名为空)
                VUsername.VALIDATOR.validate("");
            } else if (identifier.indexOf('@') >= 0
                && VEmail.VALIDATOR.validate(identifier)) {
                return SpecificLoginMode.PWD_EMAIL;
            } else if (Character.isDigit(identifier.charAt(0))
                && VPhone.VALIDATOR.validate(identifier)) {
                return SpecificLoginMode.PWD_PHONE;
            } else if (VUsername.VALIDATOR.validate(identifier)) {
                return SpecificLoginMode.PWD_USERNAME;
            }
            throw new RuntimeException("unreachable");
        case SMS:
            VPhone.VALIDATOR.validate(identifier);
            VCaptchaSMS.VALIDATOR.validate(certification);
            return SpecificLoginMode.SMS;
        case OPEN:
            CText.NOT_NULL_OR_BLANK.verify(identifier,
                "400", Messages.REQUEST_IS_ILLEGAL);
            CText.NOT_NULL_OR_BLANK.verify(certification,
                "400", Messages.REQUEST_IS_ILLEGAL);
            return SpecificLoginMode.OPEN;
        }
        throw new RuntimeException("unreachable");
    }

}
