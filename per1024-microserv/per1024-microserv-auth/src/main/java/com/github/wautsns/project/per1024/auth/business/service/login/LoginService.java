package com.github.wautsns.project.per1024.auth.business.service.login;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.auth.business.repository.redis.AccessTokenRedis;
import com.github.wautsns.project.per1024.auth.business.service.login.identifier.UserAutheIdentifier;
import com.github.wautsns.project.per1024.auth.model.po.UserAuthePO;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.dto.LoginForm;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.SpecificLoginMode;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.UserAgent;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.UserAutheStatus;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.vo.Token;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@Service
public class LoginService {

    private final AccessTokenRedis accessTokenRedis;
    private final Map<SpecificLoginMode, UserAutheIdentifier> identifiers;

    public LoginService(
            AccessTokenRedis accessTokenRedis,
            UserAutheIdentifier[] identifiers) {
        this.accessTokenRedis = accessTokenRedis;
        this.identifiers = Arrays.stream(identifiers)
            .collect(Collectors.toMap(
                UserAutheIdentifier::getSpecificLoginMode,
                identifier -> identifier));
    }

    public Token login(LoginForm form, String headerOfUserAgent) {
        UserAgent userAgent = UserAgent.extractFromHttpHeader(headerOfUserAgent);
        SpecificLoginMode specificLoginMode = form.checkAndGetSpecificLoginMode();
        UserAutheIdentifier identifier = identifiers.get(specificLoginMode);
        UserAuthePO user = identifier.identify(form.getIdentifier(), form.getCertification());
        if (user.getStatus() == UserAutheStatus.FROZEN) {
            throw new ApiX("400", MessagesOfAuth.USER_IS_FROZEN)
                .with(MessagesOfAuth.Variables.USERNAME, user.getUsername());
        }
        return accessTokenRedis.generateAndSet(user.getId(), userAgent);
    }

}
