package com.github.wautsns.project.per1024.auth.business.service;

import java.math.BigInteger;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.apix.validator.annotation.assist.BuiltInAttributeMetadata;
import com.github.wautsns.apix.validator.annotation.builtin.specific.VId;
import com.github.wautsns.apix.validator.core.Criterion;
import com.github.wautsns.project.per1024.auth.business.repository.mapper.ResourceMapper;
import com.github.wautsns.project.per1024.auth.model.po.ResourcePO;
import com.github.wautsns.project.per1024.universal.data.common.Messages;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.ResourceStatus;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VResourceName;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 03, 2019
 */
@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceMapper resourceMapper;

    public LinkedList<ResourcePO> listAvailableResorucesByName(String name) {
        ResourcePO po = identifyByName(name);
        LinkedList<ResourcePO> pos = new LinkedList<>();
        while (true) {
            if (po.getStatus() == ResourceStatus.UNAVAILABLE) {
                throw new ApiX("403", MessagesOfAuth.RESOURCE_IS_UNAVAILABLE)
                    .with(Criterion.Variables.VALUE, po.getName());
            }
            pos.addFirst(po);
            if (BigInteger.ZERO.equals(po.getPid())) { break; }
            po = identifyById(po.getPid());
        }
        return pos;
    }

    // ------------------------- BEGIN -------------------------
    // ------------------------ assists ------------------------
    // ---------------------------------------------------------

    private ResourcePO identifyById(BigInteger id) {
        VId.VALIDATOR_FOR_UNSIGNED_BIGINT.validate(id);
        ResourcePO po = resourceMapper.selectByPrimaryKey(id);
        if (po != null) { return po; }
        throw new ApiX("404", Messages.DOES_NOT_EXIST)
            .with(Criterion.Variables.VALUE, id)
            .with(BuiltInAttributeMetadata.TARGET.getName(), MessagesOfAuth.Target.RESOURCE_ID);
    }

    private ResourcePO identifyByName(String name) {
        VResourceName.VALIDATOR.validate(name);
        ResourcePO po = resourceMapper.selectOne(new ResourcePO().setName(name));
        if (po != null) { return po; }
        throw new ApiX("404", Messages.DOES_NOT_EXIST)
            .with(Criterion.Variables.VALUE, name)
            .with(BuiltInAttributeMetadata.TARGET.getName(), MessagesOfAuth.Target.RESOURCE_NAME);
    }

}
