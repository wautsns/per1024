package com.github.wautsns.project.per1024.auth.business.service.login.identifier.pwd;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.auth.business.service.login.identifier.UserAutheIdentifier;
import com.github.wautsns.project.per1024.auth.model.po.UserAuthePO;
import com.github.wautsns.project.per1024.auth.util.bcrypt.BCryptPasswordEncoder;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@RequiredArgsConstructor
abstract class AbstractPwdUserAutheIdentifier implements UserAutheIdentifier {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserAuthePO identify(String identifier, String password) {
        UserAuthePO user = doIdentifier(identifier);
        if (passwordEncoder.matches(password, user.getPassword())) { return user; }
        throw new ApiX("400", MessagesOfAuth.PASSWORD_IS_INCORRECT);
    }

    protected abstract UserAuthePO doIdentifier(String identifier);

}
