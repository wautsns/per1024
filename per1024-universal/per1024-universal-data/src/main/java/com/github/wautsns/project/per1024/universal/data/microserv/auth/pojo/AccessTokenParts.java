package com.github.wautsns.project.per1024.universal.data.microserv.auth.pojo;

import java.io.Serializable;
import java.math.BigInteger;

import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.UserAgent;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@Data
@Accessors(chain = true)
public class AccessTokenParts implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigInteger uid;
    private UserAgent userAgent;
    private String uuid;

}
