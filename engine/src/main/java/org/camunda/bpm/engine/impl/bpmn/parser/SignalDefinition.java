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
package org.camunda.bpm.engine.impl.bpmn.parser;

import org.camunda.bpm.engine.impl.el.Expression;

import java.io.Serializable;

/**
 * Represents a bpmn signal definition
 * 
 * @author Daniel Meyer
 */
public class SignalDefinition implements Serializable {

  private static final long serialVersionUID = 1L;
    
  protected String id;
  protected Expression name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name.getExpressionText();
  }

  public Expression getExpression() {
    return name;
  }

  public void setExpression(Expression name) {
    this.name = name;
  }

}
