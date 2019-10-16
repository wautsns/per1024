package com.github.wautsns.project.per1024.universal.data.microserv.auth.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigInteger;

import com.github.wautsns.apix.validator.annotation.assist.AnnotatedValidatorParser;
import com.github.wautsns.apix.validator.annotation.builtin.AAttribute;
import com.github.wautsns.apix.validator.annotation.builtin.AConstraint;
import com.github.wautsns.apix.validator.annotation.builtin.AVariable;
import com.github.wautsns.apix.validator.annotation.builtin.normal.VNotNull;
import com.github.wautsns.apix.validator.annotation.builtin.specific.VId;
import com.github.wautsns.apix.validator.core.Validator;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 06, 2019
 */
@Documented
@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE_USE })
@AConstraint
@VNotNull(target = VUid.TARGET)
@VId(target = VUid.TARGET, unsigned = true)
public @interface VUid {

    Validator<BigInteger> VALIDATOR
        = AnnotatedValidatorParser.create(VUid.class, BigInteger.class);

    @AVariable
    @AAttribute
    String TARGET = MessagesOfAuth.Target.UID;

    Class<?>[] groups() default {};

    int order() default 0;

}
