package com.github.wautsns.project.per1024.auth.business.service.login.identifier.sms;

import org.springframework.stereotype.Component;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.auth.business.service.login.identifier.UserAutheIdentifier;
import com.github.wautsns.project.per1024.auth.model.po.UserAuthePO;
import com.github.wautsns.project.per1024.universal.data.common.Messages;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.SpecificLoginMode;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@Component
public class SMSUserAutheIdentifier implements UserAutheIdentifier {

    @Override
    public SpecificLoginMode getSpecificLoginMode() {
        return SpecificLoginMode.SMS;
    }

    @Override
    public UserAuthePO identify(String identifier, String certification) {
        throw new ApiX("403", Messages.REQUEST_IS_ILLEGAL);
    }

}
