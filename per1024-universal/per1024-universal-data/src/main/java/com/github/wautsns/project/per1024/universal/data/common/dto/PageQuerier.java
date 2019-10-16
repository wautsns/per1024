package com.github.wautsns.project.per1024.universal.data.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 13, 2019
 */
@Data
@Accessors(chain = true)
public class PageQuerier {

    private Integer pageSize;
    private Integer pageNumber;

}
