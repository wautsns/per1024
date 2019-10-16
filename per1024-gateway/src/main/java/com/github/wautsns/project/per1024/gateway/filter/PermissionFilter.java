package com.github.wautsns.project.per1024.gateway.filter;

import java.math.BigInteger;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.github.wautsns.apix.core.ApiX;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.dto.AuthorizationForm;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.AuthHttpHeaders;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.literal.ResourceOperation;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.pojo.AccessTokenParts;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VAccessToken;
import com.github.wautsns.project.per1024.universal.rpc.auth.CheckPermissionRPC;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 *
 * @author wautsns
 * @version 0.1.0 Aug 30, 2019
 */
@Component
@RequiredArgsConstructor
public class PermissionFilter implements GlobalFilter, Ordered {

    @Reference(version = "1.0")
    private CheckPermissionRPC checkPermissionRPC;

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) throws ApiX {
        ServerHttpRequest request = exchange.getRequest();
        ResourceOperation operation = ResourceOperation
            .extractFromHttpMethodName(request.getMethod().name());
        String authorization = request.getHeaders().getFirst("Authorization");
        AccessTokenParts accessTokenParts = (authorization == null)
            ? null : VAccessToken.EXTRACTOR_FOR_HTTP_HEAD_AUTHORIZATION.extract(authorization);
        AuthorizationForm authorizationForm = new AuthorizationForm()
            .setAccessTokenParts(accessTokenParts)
            .setResourceName(request.getPath().value())
            .setResourceOperation(operation);
        Byte scope = checkPermissionRPC.checkPermissionAndGetScopeCode(authorizationForm);
        BigInteger operator = (accessTokenParts == null) ? null : accessTokenParts.getUid();
        exchange.getRequest().mutate()
            .headers(headers -> {
                headers.set(AuthHttpHeaders.SCOPE_CODE, scope.toString());
                if (operator != null) {
                    headers.set(AuthHttpHeaders.OPERATOR_UID, operator.toString());
                }
            });
        return chain.filter(exchange);
    }

}
