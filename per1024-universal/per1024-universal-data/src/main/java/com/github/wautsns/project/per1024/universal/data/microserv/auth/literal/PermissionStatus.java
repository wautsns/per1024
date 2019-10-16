package com.github.wautsns.project.per1024.universal.data.microserv.auth.literal;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.apix.validator.core.Extractor;
import com.github.wautsns.apix.validator.core.builtin.extractor.EEnum;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 10, 2019
 */
public enum PermissionStatus {

    ENABLED, DISABLED;

    private static final Extractor<String, PermissionStatus> EXTRACTOR_FOR_TEXT = EEnum.ofName(
        "", "", PermissionStatus.class);

    public PermissionStatus extractFromText(String text) throws ApiX {
        return EXTRACTOR_FOR_TEXT.extract(text);
    }

}
