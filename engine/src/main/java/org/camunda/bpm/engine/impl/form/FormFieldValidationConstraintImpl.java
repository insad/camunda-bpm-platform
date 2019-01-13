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
package org.camunda.bpm.engine.impl.form;

import java.io.Serializable;

import org.camunda.bpm.engine.form.FormFieldValidationConstraint;

/**
 * @author Daniel Meyer
 *
 */
public class FormFieldValidationConstraintImpl implements FormFieldValidationConstraint, Serializable {

  private static final long serialVersionUID = 1L;

  protected String name;
  protected String configuration;

  public FormFieldValidationConstraintImpl() {
  }

  public FormFieldValidationConstraintImpl(String name, String configuration) {
    this.name = name;
    this.configuration = configuration;
  }

  public String getName() {
    return name;
  }

  public Object getConfiguration() {
    return configuration;
  }

  public void setConfiguration(String configuration) {
    this.configuration = configuration;
  }

  public void setName(String name) {
    this.name = name;
  }

}
