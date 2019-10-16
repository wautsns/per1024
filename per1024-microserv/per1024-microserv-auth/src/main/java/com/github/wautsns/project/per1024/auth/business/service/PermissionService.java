package com.github.wautsns.project.per1024.auth.business.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.apix.validator.annotation.builtin.specific.VId;
import com.github.wautsns.project.per1024.auth.business.repository.mapper.PermissionMapper;
import com.github.wautsns.project.per1024.auth.business.repository.mapper.RoleMn2nPermissionMapper;
import com.github.wautsns.project.per1024.auth.model.po.PermissionPO;
import com.github.wautsns.project.per1024.auth.model.po.RoleMn2nPermissionPO;
import com.github.wautsns.project.per1024.universal.data.common.Messages;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.PermissionStatus;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.ResourceOperation;

import lombok.RequiredArgsConstructor;
import tk.mybatis.mapper.entity.Example;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 03, 2019
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionMapper permissionMapper;
    private final RoleMn2nPermissionMapper roleMn2nPermissionMapper;

    public PermissionPO identifyEnabledPermissionByResourceIdAndOperation(
            BigInteger resourceId, ResourceOperation resourceOperation) {
        VId.VALIDATOR_FOR_UNSIGNED_BIGINT.validate(resourceId);
        PermissionPO po = permissionMapper.selectOne(new PermissionPO()
            .setResourceId(resourceId)
            .setOperation(resourceOperation));
        if (po == null) { throw new ApiX("400", Messages.REQUEST_IS_ILLEGAL); }
        if (po.getStatus() == PermissionStatus.DISABLED) {
            throw new ApiX("403", MessagesOfAuth.PERMISSION_IS_DISABLED);
        }
        return po;
    }

    public Byte getMaxScopeCodeByPermissionIdAndRoleIds(
            BigInteger permissionId, List<Long> roleIds) {
        Example example = new Example(RoleMn2nPermissionPO.class);
        example.selectProperties("scopeCode");
        example.createCriteria()
            .andEqualTo("permissionId", permissionId)
            .andIn("roleId", roleIds);
        return roleMn2nPermissionMapper.selectByExample(example).stream()
            .map(RoleMn2nPermissionPO::getScopeCode)
            .max((b1, b2) -> b1.compareTo(b2))
            .orElse(null);
    }

}
