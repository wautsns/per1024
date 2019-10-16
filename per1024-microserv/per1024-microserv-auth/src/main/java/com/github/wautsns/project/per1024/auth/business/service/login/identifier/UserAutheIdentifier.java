package com.github.wautsns.project.per1024.auth.business.service.login.identifier;

import com.github.wautsns.project.per1024.auth.model.po.UserAuthePO;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.SpecificLoginMode;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 28, 2019
 */
public interface UserAutheIdentifier {

    SpecificLoginMode getSpecificLoginMode();

    UserAuthePO identify(String identifier, String certification);

}
