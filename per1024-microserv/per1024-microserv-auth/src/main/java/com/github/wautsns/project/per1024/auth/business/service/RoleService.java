package com.github.wautsns.project.per1024.auth.business.service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.wautsns.project.per1024.auth.business.repository.mapper.UserMn2nRoleMapper;
import com.github.wautsns.project.per1024.auth.model.po.UserMn2nRolePO;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 11, 2019
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private static final List<Long> VISITOR = Collections.singletonList(1L);

    private final UserMn2nRoleMapper userMn2nRoleMapper;

    public List<Long> listIdsByUid(BigInteger uid) {
        if (uid == null) { return VISITOR; }
        List<UserMn2nRolePO> roles = userMn2nRoleMapper.select(new UserMn2nRolePO().setUid(uid));
        return roles.stream()
            .map(UserMn2nRolePO::getRoleId)
            .collect(Collectors.toList());
    }

}
