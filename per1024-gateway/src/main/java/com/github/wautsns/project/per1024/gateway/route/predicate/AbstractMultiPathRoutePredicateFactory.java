/**
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.wautsns.project.per1024.gateway.route.predicate;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
import static org.springframework.http.server.PathContainer.parsePath;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.PathContainer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPattern.PathMatchInfo;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * Multi path route predicate abstract factory.
 *
 * <p>
 * Used for multipath matching to the same api service.
 *
 * @author wautsns
 * @version 0.1.0 Apr 14, 2019
 */
public abstract class AbstractMultiPathRoutePredicateFactory<C> extends AbstractRoutePredicateFactory<C> {

    private static final Log log = LogFactory.getLog(AbstractMultiPathRoutePredicateFactory.class);

    private PathPatternParser pathPatternParser = new PathPatternParser();

    public AbstractMultiPathRoutePredicateFactory(Class<C> configClass) { super(configClass); }

    protected abstract List<String> getMultiPaths(C config);

    @Override
    public Predicate<ServerWebExchange> apply(C config) {
        LinkedList<PathPattern> pathPatterns;
        synchronized (this.pathPatternParser) {
            pathPatterns = getMultiPaths(config).stream()
                .map(pathPatternParser::parse)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        return exchange -> {
            PathContainer path = parsePath(exchange.getRequest().getURI().getPath());
            for (PathPattern pathPattern : pathPatterns) {
                boolean match = pathPattern.matches(path);
                traceMatch("Pattern", pathPattern.getPatternString(), path, match);
                if (!match) continue;
                PathMatchInfo uriTemplateVariables = pathPattern.matchAndExtract(path);
                exchange.getAttributes().put(URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriTemplateVariables);
                return true;
            }
            return false;
        };
    }

    private static void traceMatch(String prefix, Object desired, Object actual, boolean match) {
        if (log.isTraceEnabled()) {
            String message = String.format("%s \"%s\" %s against value \"%s\"",
                prefix, desired, match ? "matches" : "does not match", actual);
            log.trace(message);
        }
    }

}
