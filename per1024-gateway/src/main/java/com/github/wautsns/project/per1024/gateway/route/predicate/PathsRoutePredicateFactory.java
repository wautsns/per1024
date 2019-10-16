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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Implimentation of {@link AbstractMultiPathRoutePredicateFactory}.
 *
 * <p>
 * e.g. If {@code Paths= /xx-x/abc/** & yy-y & zzz}, all {@code /xx-x/abc/**}, {@code /yy-y/**},
 * {@code /zzz/**} can match the api service.
 *
 * @author wautsns
 * @version 0.1.0 Apr 14, 2019
 */
@Component
public class PathsRoutePredicateFactory
        extends AbstractMultiPathRoutePredicateFactory<
            PathsRoutePredicateFactory.Config> {

    public PathsRoutePredicateFactory() { super(Config.class); }

    // eg. predicates:
    // - Paths: xxxx, ${paths}, ${delimiter}, 指定这个参数顺序
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(Config.FIELD_NAME_PATHS, Config.FIELD_NAME_DELIMITER);
    }

    @Override
    protected List<String> getMultiPaths(Config config) {
        return Arrays.stream(config.paths.split(config.delimiter))
            .map(String::trim)
            .map(path -> path.startsWith("/") ? path : '/' + path + "/**")
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Getter
    @Setter
    public static class Config {

        private static final String FIELD_NAME_PATHS = "paths";
        private static final String FIELD_NAME_DELIMITER = "delimiter";

        private String paths;

        private String delimiter = "&";
    }

}
