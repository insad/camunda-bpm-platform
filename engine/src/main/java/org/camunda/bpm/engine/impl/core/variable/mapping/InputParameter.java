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
package org.camunda.bpm.engine.impl.core.variable.mapping;

import org.camunda.bpm.engine.impl.core.CoreLogger;
import org.camunda.bpm.engine.impl.core.variable.mapping.value.ParameterValueProvider;
import org.camunda.bpm.engine.impl.core.variable.scope.AbstractVariableScope;

/**
 *
 * <pre>
 *               +-----------------+
 *               |                 |
 *  outer scope-----> inner scope  |
 *               |                 |
 *               +-----------------+
 * </pre>
 *
 * @author Daniel Meyer
 */
public class InputParameter extends IoParameter {

  private final static CoreLogger LOG = CoreLogger.CORE_LOGGER;

  public InputParameter(String name, ParameterValueProvider valueProvider) {
    super(name, valueProvider);
  }

  protected void execute(AbstractVariableScope innerScope, AbstractVariableScope outerScope) {

    // get value from outer scope
    Object value = valueProvider.getValue(outerScope);

    LOG.debugMappingValueFromOuterScopeToInnerScope(value,outerScope, name, innerScope);

    // set variable in inner scope
    innerScope.setVariableLocal(name, value);
  }

}
