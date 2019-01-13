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
package org.camunda.bpm.integrationtest.functional.cdi.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * A {@link RequestScoped} bean
 * 
 * @author Daniel Meyer
 *
 */
@Named
@RequestScoped
public class RequestScopedDelegateBean implements JavaDelegate {

  private int invocationCounter = 0;
  
  @Override
  public void execute(DelegateExecution execution) throws Exception {
    invocationCounter++;
    execution.setVariable("invocationCounter", invocationCounter);
  }

}
