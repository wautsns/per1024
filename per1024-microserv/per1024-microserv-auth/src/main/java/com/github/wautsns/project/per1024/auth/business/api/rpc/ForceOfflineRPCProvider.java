package com.github.wautsns.project.per1024.auth.business.api.rpc;

import java.math.BigInteger;

import org.apache.dubbo.config.annotation.Service;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.auth.business.service.LogoutService;
import com.github.wautsns.project.per1024.universal.rpc.auth.ForceOfflineRPC;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 12, 2019
 */
@Service(version = "1.0")
@RequiredArgsConstructor
public class ForceOfflineRPCProvider implements ForceOfflineRPC {

    private final LogoutService logoutService;

    @Override
    public void forceOffline(Iterable<BigInteger> uids) throws ApiX {
        logoutService.forceOffline(uids);
    }

}
