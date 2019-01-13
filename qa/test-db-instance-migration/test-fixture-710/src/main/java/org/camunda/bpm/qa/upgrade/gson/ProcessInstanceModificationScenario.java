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
package org.camunda.bpm.qa.upgrade.gson;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.qa.upgrade.DescribesScenario;
import org.camunda.bpm.qa.upgrade.ScenarioSetup;

/**
 * @author Tassilo Weidner
 */
public class ProcessInstanceModificationScenario {

  @Deployment
  public static String deploy() {
    return "org/camunda/bpm/qa/upgrade/gson/oneTaskProcessInstanceModification.bpmn20.xml";
  }

  @DescribesScenario("ProcessInstanceModificationScenario")
  public static ScenarioSetup initProcessInstanceModification() {
    return new ScenarioSetup() {
      public void execute(ProcessEngine engine, String scenarioName) {

        String processDefinitionId = engine.getRepositoryService().createProcessDefinitionQuery()
          .processDefinitionKey("oneTaskProcessInstanceModification_710")
          .singleResult()
          .getId();

        String processInstanceId = engine.getRuntimeService()
          .startProcessInstanceById(processDefinitionId, "ProcessInstanceModificationScenario").getId();

        engine.getRuntimeService().createModification(processDefinitionId)
          .processInstanceIds(processInstanceId)
          .startBeforeActivity("userTask2")
          .execute();

        ActivityInstance tree = engine.getRuntimeService().getActivityInstance(processInstanceId);

        engine.getRuntimeService().createProcessInstanceModification(processInstanceId)
          .cancelActivityInstance(tree.getActivityInstances("userTask1")[0].getId())
          .cancelTransitionInstance(tree.getTransitionInstances("userTask2")[0].getId())
          .executeAsync();
      }
    };
  }
}
