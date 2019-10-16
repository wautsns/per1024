package com.github.wautsns.project.per1024.auth.business.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.okauth.core.manager.OkAuthManager;
import com.github.wautsns.project.per1024.universal.data.common.HttpHeaders;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.dto.LoginForm;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.LoginMode;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.vo.Token;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 10, 2019
 */
@Controller
@RequestMapping("/commands/auth")
@RequiredArgsConstructor
public class AuthCommandsController {

    private final OkAuthManager okAuthManager;
    private final TokensController tokenController;

    @GetMapping("/redirect-to-oauth-login-page")
    public String redirectToOauthLoginPage(
            @RequestParam String openPlatform) {
        String authorizeUrl = okAuthManager.getAuthorizeUrl(openPlatform);
        if (authorizeUrl != null) { return "redirect:" + authorizeUrl; }
        throw new ApiX("400", MessagesOfAuth.UNSUPPORTED_OPEN_PLATFORM)
            .with(MessagesOfAuth.Variables.OPEN_PLATFORM, openPlatform);
    }

    @GetMapping("/handle-oauth-authorization-callback")
    @ResponseBody
    public Token handleOauthCallback(
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestParam String openPlatform,
            @RequestParam String code) {
        return tokenController.login(userAgent, new LoginForm()
            .setMode(LoginMode.OPEN.getCode())
            .setIdentifier(openPlatform)
            .setCertification(code));
    }

}
