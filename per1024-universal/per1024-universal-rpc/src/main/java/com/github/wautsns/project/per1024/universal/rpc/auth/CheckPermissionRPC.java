package com.github.wautsns.project.per1024.universal.rpc.auth;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.dto.AuthorizationForm;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 28, 2019
 */
public interface CheckPermissionRPC {

    @SentinelResource("rpc-checkPermissionAndGetScopeCode")
    Byte checkPermissionAndGetScopeCode(AuthorizationForm form) throws ApiX;

}
