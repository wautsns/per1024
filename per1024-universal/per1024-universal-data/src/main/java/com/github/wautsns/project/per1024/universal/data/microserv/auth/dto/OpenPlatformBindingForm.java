/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wautsns.project.per1024.universal.data.microserv.auth.dto;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.apix.validator.annotation.assist.AnnotatedValidatorParser;
import com.github.wautsns.apix.validator.annotation.builtin.text.VNotNullOrBlank;
import com.github.wautsns.apix.validator.core.Validator;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.MessagesOfAuth;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VUid;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 27, 2019
 */
@Data
@Accessors(chain = true)
@JsonNaming(SnakeCaseStrategy.class)
public class OpenPlatformBindingForm implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 915023195752844945L;

    public static final Validator<OpenPlatformBindingForm> VALIDATOR
        = AnnotatedValidatorParser.parse(OpenPlatformBindingForm.class);

    @VUid
    private BigInteger uid;
    @VNotNullOrBlank(target = MessagesOfAuth.Target.OPEN_PLATFORM)
    private String openPlatform;
    @VNotNullOrBlank(target = MessagesOfAuth.Target.OPEN_ID)
    private String openId;

}
