/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.toolkit.oasv.style.validator.components;

import org.apache.servicecomb.toolkit.oasv.validation.skeleton.components.ComponentsResponsesKeysValidator;

import static org.apache.servicecomb.toolkit.oasv.util.StringCaseUtils.isMatchCase;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#componentsObject">Components Object</a>
 * .responses property key case validator
 * <ul>
 *   <li>config item: components.responses.key.case=upper-camel-case/lower-camel-case/upper-hyphen-case</li>
 *   <li>must match case</li>
 * </ul>
 */
public class ComponentsResponsesKeysCaseValidator extends ComponentsResponsesKeysValidator {

  public static final String CONFIG_KEY = "components.responses.key.case";
  public static final String ERROR = "Must be ";

  public ComponentsResponsesKeysCaseValidator(String expectedCase) {
    super(
        key -> isMatchCase(expectedCase, key),
        key -> ERROR + expectedCase
    );
  }

}
