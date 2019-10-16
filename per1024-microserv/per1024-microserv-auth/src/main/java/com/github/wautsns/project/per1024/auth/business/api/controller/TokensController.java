package com.github.wautsns.project.per1024.auth.business.api.controller;

import java.math.BigInteger;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.wautsns.project.per1024.auth.business.service.LogoutService;
import com.github.wautsns.project.per1024.auth.business.service.login.LoginService;
import com.github.wautsns.project.per1024.universal.data.common.HttpHeaders;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.dto.LoginForm;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.vo.Token;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 23, 2019
 */
@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokensController {

    private final LoginService loginService;
    private final LogoutService logoutService;

    @GetMapping
    @SentinelResource("handle_test")
    public String test() {
        return "test";
    }

    @PostMapping
    @SentinelResource("handle_login")
    public Token login(
            @RequestHeader("User-Agent") String userAgent,
            LoginForm form) {
        return loginService.login(form, userAgent);
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void forceOffline(
            @RequestParam(name = "uid") BigInteger[] uids) {
        logoutService.forceOffline(Arrays.asList(uids));
    }

    @DeleteMapping("/operator")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void logout(
            @RequestHeader(HttpHeaders.OPERATOR_UID) BigInteger operatorUid,
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        logoutService.logout(operatorUid, userAgent);
    }

}
