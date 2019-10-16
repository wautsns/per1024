package com.github.wautsns.project.per1024.universal.data.microserv.auth.vo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 24, 2019
 */
@Data
@Accessors(chain = true)
@JsonNaming(SnakeCaseStrategy.class)
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;
    private Long expiredOn;

}
