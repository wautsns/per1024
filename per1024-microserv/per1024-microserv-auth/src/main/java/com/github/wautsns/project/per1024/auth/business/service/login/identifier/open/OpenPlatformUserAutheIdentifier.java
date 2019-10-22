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

    /**
     * 根据开放平台名称和授权码获取用户信息
     *
     * @param openPlatform 开放平台名称
     * @param code oauth2.0 授权码
     * @return 用户认证信息
     */
    @Override
    public UserAuthePO identify(String openPlatform, String code) {
        // 使用 OkAuthManager 获取指定开放平台名称的客户端
        AbstractOkAuthClient<?, ?> client = okAuthManager.get(openPlatform);
        if (client == null) {
            // 无对应客户端, 则说明该给定的开放平台不支持
            throw new ApiX("400", MessagesOfAuth.UNSUPPORTED_OPEN_PLATFORM)
                .with(MessagesOfAuth.Variables.OPEN_PLATFORM, openPlatform);
        }
        try {
            // 使用 code 获取用户信息(exchangeForUser 实际包含两步,1. 获取access_token 2.获取用户信息)
            // exchangeForUser 不会返回 null, 若 code 有错误会抛出 OkAuthException
            OkAuthUser user = client.exchangeForUser(code);
            UserOauthPO userOauthPO = userOauthMapper.selectOne(new UserOauthPO()
                .setOpenId(user.getOpenId())
                .setPlatform(openPlatform));
            if (userOauthPO == null) {
                // 为 null 则说明该第三方平台账号未绑定到该系统内
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
