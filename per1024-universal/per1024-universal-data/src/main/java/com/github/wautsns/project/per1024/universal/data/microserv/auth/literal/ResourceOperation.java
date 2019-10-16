package com.github.wautsns.project.per1024.universal.data.microserv.auth.literal;

import com.github.wautsns.apix.validator.annotation.assist.BuiltInAttributeMetadata;
import com.github.wautsns.apix.validator.annotation.builtin.enumeration.VCodeOfEnum;
import com.github.wautsns.apix.validator.core.Extractor;
import com.github.wautsns.apix.validator.core.builtin.extractor.EEnum;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 28, 2019
 */
public enum ResourceOperation {

    GET, POST, PUT, PATCH, DELETE;

    private static final Extractor<String, ResourceOperation> EXTRACTOR_FOR_HTTP_METHOD_NAME
        = EEnum.ofName(
            "405", VCodeOfEnum.DEFAULT_MESSAGE, ResourceOperation.class)
            .mutateWith(
                BuiltInAttributeMetadata.TARGET.getName(),
                MessagesOfAuth.Target.RESOURCE_OPERATION);

    public static ResourceOperation extractFromHttpMethodName(String name) {
        return EXTRACTOR_FOR_HTTP_METHOD_NAME.extract(name);
    }

}
