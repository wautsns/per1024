package com.github.wautsns.project.per1024.universal.data.microserv.auth.dto;

import java.io.Serializable;

import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.ResourceOperation;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.pojo.AccessTokenParts;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 28, 2019
 */
@Data
@Accessors(chain = true)
public class AuthorizationForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private AccessTokenParts accessTokenParts;
    private String resourceName;
    private ResourceOperation resourceOperation;

}
