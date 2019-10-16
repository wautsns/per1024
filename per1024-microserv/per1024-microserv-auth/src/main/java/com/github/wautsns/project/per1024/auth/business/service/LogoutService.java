package com.github.wautsns.project.per1024.auth.business.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.auth.business.repository.redis.AccessTokenRedis;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.UserAgent;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VUid;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 03, 2019
 */
@Service
@RequiredArgsConstructor
public class LogoutService {

    private final AccessTokenRedis accessTokenRedis;

    public void forceOffline(Iterable<BigInteger> uids) throws ApiX {
        uids.forEach(VUid.VALIDATOR::validate);
        accessTokenRedis.deleteByUids(uids);
    }

    public void logout(BigInteger uid, String userAgentText) {
        VUid.VALIDATOR.validate(uid);
        UserAgent userAgent = UserAgent.extractFromHttpHeader(userAgentText);
        accessTokenRedis.deleteByUidAndUserAgent(uid, userAgent);
    }

}
