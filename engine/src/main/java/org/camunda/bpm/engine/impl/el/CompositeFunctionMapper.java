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
package org.camunda.bpm.engine.impl.el;

import java.lang.reflect.Method;
import java.util.List;

import org.camunda.bpm.engine.impl.javax.el.FunctionMapper;


/**
 * A {@link FunctionMapper} implemenation which delegates to a list of
 * mappers. When a function is resolved, the list of mappers is iterated
 * and the first one to return a method is used.
 *
 * @author Daniel Meyer
 */
public class CompositeFunctionMapper extends FunctionMapper {

  protected List<FunctionMapper> delegateMappers;

  public CompositeFunctionMapper(List<FunctionMapper> delegateMappers) {
    this.delegateMappers = delegateMappers;
  }

  public Method resolveFunction(String prefix, String localName) {
    for (FunctionMapper mapper : delegateMappers) {
      Method resolvedFunction = mapper.resolveFunction(prefix, localName);
      if(resolvedFunction != null) {
        return resolvedFunction;
      }
    }
    return null;
  }

}
