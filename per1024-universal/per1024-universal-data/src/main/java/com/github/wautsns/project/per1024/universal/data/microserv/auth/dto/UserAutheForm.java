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

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wautsns.apix.validator.annotation.assist.AnnotatedValidatorParser;
import com.github.wautsns.apix.validator.core.Validator;
import com.github.wautsns.project.per1024.universal.data.common.validator.VEmail;
import com.github.wautsns.project.per1024.universal.data.common.validator.VPhone;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VPassword;
import com.github.wautsns.project.per1024.universal.data.microserv.auth.validator.VUsername;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author wautsns
 * @version 0.1.0 Sep 26, 2019
 */
@Data
@Accessors(chain = true)
@JsonNaming(SnakeCaseStrategy.class)
public class UserAutheForm implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 7114586514253846701L;

    public static final Validator<UserAutheForm> VALIDATOR
        = AnnotatedValidatorParser.parse(UserAutheForm.class);

    @VUsername
    private String username;
    @VPassword
    private String password;
    @VPhone
    private String phone;
    @VEmail
    private String email;

}
