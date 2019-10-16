package com.github.wautsns.project.per1024.auth.business.service.login.identifier.open;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.okauth.core.client.AbstractOkAuthClient;
import com.github.wautsns.okauth.core.client.dto.OkAuthUser;
import com.github.wautsns.okauth.core.exception.OkAuthException;
import com.github.wautsns.okauth.core.manager.OkAuthManager;
import com.github.wautsns.project.per1024.auth.business.repository.mapper.UserAutheMapper;
import com.github.wautsns.project.per1024.auth.business.repository.mapper.UserOauthMapper;
import com.github.wautsns.project.per1024.auth.business.service.login.identifier.UserAutheIdentifier;
import com.github.wautsns.project.per1024.auth.model.po.UserAuthePO;
import com.github.wautsns.project.per1024.auth.model.po.UserOauthPO;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.SpecificLoginMode;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 29, 2019
 */
@Component
@RequiredArgsConstructor
public class OpenPlatformUserAutheIdentifier implements UserAutheIdentifier {

    private final OkAuthManager okAuthManager;
    private final UserOauthMapper userOauthMapper;
    private final UserAutheMapper userAuthMapper;

    @Override
    public SpecificLoginMode getSpecificLoginMode() {
        return SpecificLoginMode.OPEN;
    }

    @Override
    public UserAuthePO identify(String openPlatform, String code) {
        try {
            AbstractOkAuthClient<?, ?> client = okAuthManager.get(openPlatform);
            if (client == null) {
                throw new ApiX("400", MessagesOfAuth.UNSUPPORTED_OPEN_PLATFORM)
                    .with(MessagesOfAuth.Variables.OPEN_PLATFORM, openPlatform);
            }
            OkAuthUser user = client.exchangeForUser(code);
            UserOauthPO userOauthPO = userOauthMapper.selectOne(new UserOauthPO()
                .setOpenId(user.getOpenId())
                .setPlatform(openPlatform));
            if (userOauthPO == null) {
                throw new ApiX("400", MessagesOfAuth.OPEN_ID_UNBOUND_USER)
                    .with(MessagesOfAuth.Variables.OPEN_PLATFORM, openPlatform)
                    .with(MessagesOfAuth.Variables.OPEN_USER_INFO, user);
            }
            return userAuthMapper.selectByPrimaryKey(userOauthPO.getUid());
        } catch (OkAuthException e) {
            throw new ApiX("400", e.getMessage());
        } catch (IOException e) {
            throw new ApiX("503", e.getMessage());
        }
    }

}
