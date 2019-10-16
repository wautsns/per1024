package com.github.wautsns.project.per1024.universal.rpc.auth;

import java.math.BigInteger;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.wautsns.apix.core.ApiX;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 03, 2019
 */
public interface ForceOfflineRPC {

    @SentinelResource("rpc-forceOffline")
    void forceOffline(Iterable<BigInteger> uids) throws ApiX;

}
