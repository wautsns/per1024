package com.github.wautsns.project.per1024.auth.business.api.rpc;

import java.math.BigInteger;
import java.util.List;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.auth.business.repository.redis.AccessTokenRedis;
import com.github.wautsns.project.per1024.auth.business.service.PermissionService;
import com.github.wautsns.project.per1024.auth.business.service.ResourceService;
import com.github.wautsns.project.per1024.auth.business.service.RoleService;
import com.github.wautsns.project.per1024.auth.model.po.PermissionPO;
import com.github.wautsns.project.per1024.auth.model.po.ResourcePO;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.dto.AuthorizationForm;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.pojo.AccessTokenParts;
import com.github.wautsns.project.per1024.universal.rpc.auth.CheckPermissionRPC;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 11, 2019
 */
@Service(version = "1.0")
@RequiredArgsConstructor
public class CheckPermissionRPCProvider implements CheckPermissionRPC {

    private final AccessTokenRedis accessTokenRedis;
    private final ResourceService resourceService;
    private final PermissionService permissionService;
    private final RoleService roleService;

    @Override
    @SentinelResource("")
    public Byte checkPermissionAndGetScopeCode(AuthorizationForm form) throws ApiX {
        ResourcePO resource = resourceService
            .listAvailableResorucesByName(form.getResourceName())
            .getLast();
        PermissionPO permission = permissionService
            .identifyEnabledPermissionByResourceIdAndOperation(
                resource.getId(), form.getResourceOperation());
        AccessTokenParts accessTokenParts = form.getAccessTokenParts();
        boolean validAccessToken = accessTokenRedis.exists(accessTokenParts);
        BigInteger uid = validAccessToken ? accessTokenParts.getUid() : null;
        List<Long> roleIds = roleService.listIdsByUid(uid);
        Byte scopeCode = permissionService
            .getMaxScopeCodeByPermissionIdAndRoleIds(permission.getId(), roleIds);
        if (scopeCode != null) {
            return scopeCode;
        } else if (validAccessToken) {
            throw new ApiX("403", MessagesOfAuth.INSUFFICIENT_PERMISSION);
        } else {
            throw new ApiX("401", MessagesOfAuth.UNAUTHORIZED);
        }
    }

}
