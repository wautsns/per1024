package com.github.wautsns.project.per1024.auth.business.repository.redis;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.UserAgent;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.pojo.AccessTokenParts;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.vo.Token;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@Repository
@RequiredArgsConstructor
public class AccessTokenRedis {

    private static final String PREFIX = "per1024:microserv:auth:access-token:";
    private static final long TTL_MINUTES = 10080;

    private final StringRedisTemplate redis;

    public Token generateAndSet(BigInteger uid, UserAgent userAgent) {
        String key = generateKey(uid, userAgent);
        String val = UUID.randomUUID().toString();
        redis.opsForValue().set(key, val, TTL_MINUTES, TimeUnit.MINUTES);
        return new Token()
            .setAccessToken(key.substring(PREFIX.length()) + ':' + val)
            .setExpiredOn(System.currentTimeMillis() + TTL_MINUTES * 60_000);
    }

    public void deleteByUidAndUserAgent(BigInteger uid, UserAgent userAgent) {
        String key = generateKey(uid, userAgent);
        redis.delete(key);
    }

    public void deleteByUids(Iterable<BigInteger> uids) {
        List<String> keys = new LinkedList<>();
        UserAgent[] userAgents = UserAgent.values();
        for (BigInteger uid : uids) {
            for (UserAgent userAgent : userAgents) {
                keys.add(generateKey(uid, userAgent));
            }
        }
        redis.delete(keys);
    }

    public boolean exists(AccessTokenParts accessTokenParts) {
        if (accessTokenParts == null) { return false; }
        String key = generateKey(accessTokenParts.getUid(), accessTokenParts.getUserAgent());
        String val = redis.opsForValue().get(key);
        return val != null && val.equals(accessTokenParts.getUuid());
    }

    private String generateKey(BigInteger uid, UserAgent userAgent) {
        return new StringBuilder()
            .append(PREFIX).append(uid)
            .append(':').append((userAgent == null) ? '*' : userAgent.getCode())
            .toString();
    }

}
