/*
 * Copyright © 2013-2018 camunda services GmbH and various authors (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.cdi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import org.camunda.bpm.engine.variable.value.TypedValue;

/**
 * Annotation for qualifying injection points such that process variables are
 * injected. Instead of a normal Java {@link Object} a {@link TypedValue} will
 * be returned.
 * <ul>
 * <li>{@code @Inject @ProcessVariableTyped TypedValue accountNumber}</li>
 * <li>{@code @Inject @ProcessVariableTyped("accountNumber") TypedValue account}</li>
 * </ul>
 * In both cases, the process variable with the name 'accountNumber' is injected
 * as TypedValue. NOTE: injection points must be of type 'TypedValue'.
 *
 * @author Michael Scholz
 *
 * @since 7.3
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProcessVariableTyped {

  /**
   * The name of the process variable to look up. Defaults to the name of the
   * annotated field or parameter
   */
  @Nonbinding
  public String value() default "";

}