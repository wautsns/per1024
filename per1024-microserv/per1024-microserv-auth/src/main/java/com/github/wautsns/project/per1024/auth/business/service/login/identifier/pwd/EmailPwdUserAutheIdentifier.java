package com.github.wautsns.project.per1024.auth.business.service.login.identifier.pwd;

import org.springframework.stereotype.Component;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.apix.validator.annotation.assist.BuiltInAttributeMetadata;
import com.github.wautsns.apix.validator.core.Criterion;
import com.github.wautsns.project.per1024.auth.business.repository.mapper.UserAutheMapper;
import com.github.wautsns.project.per1024.auth.model.po.UserAuthePO;
import com.github.wautsns.project.per1024.auth.util.bcrypt.BCryptPasswordEncoder;
import com.github.wautsns.project.per1024.universal.data.common.Messages;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.SpecificLoginMode;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@Component
public class EmailPwdUserAutheIdentifier extends AbstractPwdUserAutheIdentifier {

    private final UserAutheMapper userAutheMapper;

    public EmailPwdUserAutheIdentifier(
            BCryptPasswordEncoder passwordEncoder,
            UserAutheMapper userAutheMapper) {
        super(passwordEncoder);
        this.userAutheMapper = userAutheMapper;
    }

    @Override
    public SpecificLoginMode getSpecificLoginMode() {
        return SpecificLoginMode.PWD_EMAIL;
    }

    @Override
    protected UserAuthePO doIdentifier(String email) {
        UserAuthePO user = userAutheMapper.selectOne(new UserAuthePO().setEmail(email));
        if (user != null) { return user; }
        throw new ApiX("400", Messages.DOES_NOT_EXIST)
            .with(Criterion.Variables.VALUE, email)
            .with(BuiltInAttributeMetadata.TARGET.getName(), Messages.Target.EMAIL);
    }

}
