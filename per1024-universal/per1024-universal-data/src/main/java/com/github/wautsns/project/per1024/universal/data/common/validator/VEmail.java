package com.github.wautsns.project.per1024.universal.data.common.validator;

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

import com.github.wautsns.apix.validator.annotation.assist.AnnotatedValidatorParser;
import com.github.wautsns.apix.validator.annotation.builtin.AAttribute;
import com.github.wautsns.apix.validator.annotation.builtin.AConstraint;
import com.github.wautsns.apix.validator.annotation.builtin.AVariable;
import com.github.wautsns.apix.validator.annotation.builtin.text.VNotNullOrBlank;
import com.github.wautsns.apix.validator.annotation.builtin.text.VPattern;
import com.github.wautsns.apix.validator.core.Validator;
import com.github.wautsns.project.per1024.universal.data.common.Messages;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 21, 2019
 */
@Documented
@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE_USE })
@AConstraint
@VNotNullOrBlank
@VPattern(target = VEmail.TARGET, regex = "^\\w+@\\w+[.]\\w+$")
public @interface VEmail {

    Validator<String> VALIDATOR = AnnotatedValidatorParser.create(VEmail.class, String.class);

    @AVariable
    @AAttribute
    String TARGET = Messages.Target.EMAIL;

    Class<?>[] groups() default {};

    int order() default 0;

}
