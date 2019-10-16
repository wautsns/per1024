package com.github.wautsns.project.per1024.universal.sentinelsupport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.apix.springbootstarter.util.ExceptionConvertor;
import com.github.wautsns.project.per1024.universal.data.common.Messages;

/**
 *
 * @author wautsns
 * @version 0.1.0 Oct 16, 2019
 */
@Configuration
public class BlockExceptionHandlerAutoConfiguration {

    @Bean
    public ExceptionConvertor<BlockException> sentinelBlockExceptionConvertor() {
        return new ExceptionConvertor<BlockException>() {

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public boolean applyTo(Throwable exception) {
                return exception instanceof BlockException;
            }

            @Override
            public ApiX convert(BlockException exception) {
                return new ApiX("503", Messages.SERVICE_UNAVAILABLE);
            }

        };
    }

}
