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
package org.camunda.bpm.integrationtest.functional.ejb.request.beans;

import javax.inject.Named;
import javax.naming.InitialContext;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.integrationtest.util.TestConstants;


/**
 *
 * @author Daniel Meyer
 *
 */
@Named
public class InvocationCounterDelegateBean implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    InvocationCounterService invocationCounterService = (InvocationCounterService) new InitialContext().lookup("java:global/" +
        TestConstants.getAppName() +
        "service/" +
        "InvocationCounterServiceBean!org.camunda.bpm.integrationtest.functional.ejb.request.beans.InvocationCounterService");

    execution.setVariable("invocationCounter", invocationCounterService.getNumOfInvocations());
  }

}
