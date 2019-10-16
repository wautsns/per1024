package com.github.wautsns.project.per1024.universal.data.common.validator;

import java.math.BigInteger;

import com.github.wautsns.apix.validator.annotation.assist.AnnotatedValidatorParser;
import com.github.wautsns.apix.validator.annotation.builtin.specific.VId;
import com.github.wautsns.apix.validator.core.Validator;
import com.github.wautsns.apix.validator.core.util.CollectionUtils;

import lombok.experimental.UtilityClass;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 10, 2019
 */
@UtilityClass
public class BuiltInValidators {

    public final Validator<BigInteger> UNSIGNED_BIGINT_ID = AnnotatedValidatorParser.create(
        VId.class, BigInteger.class, CollectionUtils.mapping("unsigned", true));

}
