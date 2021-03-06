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

package org.apache.servicecomb.toolkit.oasv.validation.skeleton.header;

import org.apache.servicecomb.toolkit.oasv.validation.api.SchemaValidator;
import org.apache.servicecomb.toolkit.oasv.validation.api.ObjectPropertyValidator;
import org.apache.servicecomb.toolkit.oasv.common.OasObjectType;
import org.apache.servicecomb.toolkit.oasv.validation.api.HeaderValidator;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;

import static org.apache.servicecomb.toolkit.oasv.common.OasObjectType.SCHEMA;

/**
 * <a href="https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.2.md#headerObject">Header Object</a>校验器
 * .schema property validator
 */
public class HeaderSchemaValidator extends ObjectPropertyValidator<Header, Schema> implements HeaderValidator {

  public HeaderSchemaValidator(List<SchemaValidator> oasObjectValidators) {
    super(oasObjectValidators);
  }

  @Override
  protected String get$ref(Header oasObject) {
    return oasObject.get$ref();
  }

  @Override
  protected Schema getPropertyObject(Header oasObject) {
    return oasObject.getSchema();
  }

  @Override
  protected String getPropertyName() {
    return "schema";
  }

  @Override
  protected OasObjectType getPropertyType() {
    return SCHEMA;
  }
}
