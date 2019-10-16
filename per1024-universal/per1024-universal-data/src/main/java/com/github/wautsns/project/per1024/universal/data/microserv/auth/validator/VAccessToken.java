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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.apix.validator.annotation.assist.ConstrainedElement;
import com.github.wautsns.apix.validator.annotation.builtin.AConstraint;
import com.github.wautsns.apix.validator.annotation.builtin.text.AbstractTextValidatorInitializer;
import com.github.wautsns.apix.validator.core.Extractor;
import com.github.wautsns.apix.validator.core.builtin.criterion.CAny;
import com.github.wautsns.apix.validator.core.builtin.criterion.CText;
import com.github.wautsns.apix.validator.core.util.ValidatorBuilder;
import com.github.wautsns.project.per1024.universal.data.common.Messages;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.UserAgent;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.pojo.AccessTokenParts;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VAccessToken.VI;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 21, 2019
 */
@Documented
@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE_USE })
@AConstraint(validatorInitializer = VI.class)
public @interface VAccessToken {

    Extractor<String, AccessTokenParts> EXTRACTOR_FOR_HTTP_HEAD_AUTHORIZATION = authorization -> {
        CAny.NOT_NULL.verify(authorization, "401", MessagesOfAuth.UNAUTHORIZED);
        CText.NOT_BLANK.verify(authorization, "400", Messages.REQUEST_IS_ILLEGAL);
        if (authorization.startsWith("Bearer ")) {
            Matcher matcher = VI.PATTERN.matcher(authorization);
            if (matcher.find()) {
                // 正则表达式保证了一定是数值
                BigInteger uid = new BigInteger(matcher.group(1));
                if (VUid.VALIDATOR.test(uid)) {
                    UserAgent userAgent = UserAgent.extractFromHttpHeader(matcher.group(2));
                    return new AccessTokenParts()
                        .setUid(uid)
                        .setUserAgent(userAgent)
                        .setUuid(matcher.group(3));
                }
            }
        }
        throw new ApiX("400", Messages.REQUEST_IS_ILLEGAL);
    };

    Class<?>[] groups() default {};

    int order() default 0;

    class VI extends AbstractTextValidatorInitializer<VAccessToken> {

        private static final Pattern PATTERN = Pattern.compile(
            "Bearer (\\d{1,19}):([^:]+):([0-9a-f]{8}(?:-[0-9a-f]{4}){3}-[0-9a-f]{12})");

        @Override
        protected <V extends CharSequence> void doInitForCharSequenceOrSubclass(
                ConstrainedElement<V> element, VAccessToken va,
                ValidatorBuilder<V> validatorBuilder) {
            validatorBuilder.add(value -> {
                try {
                    EXTRACTOR_FOR_HTTP_HEAD_AUTHORIZATION.extract(value.toString());
                    return null;
                } catch (ApiX apix) {
                    return apix;
                }
            });
        }

    }

}
