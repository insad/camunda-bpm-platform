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
package org.camunda.bpm.qa.upgrade.gson.batch;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.qa.upgrade.DescribesScenario;
import org.camunda.bpm.qa.upgrade.ScenarioSetup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tassilo Weidner
 */
public class UpdateProcessInstanceSuspendStateBatchScenario {

  @Deployment
  public static String deploy() {
    return "org/camunda/bpm/qa/upgrade/gson/oneTaskProcess.bpmn20.xml";
  }

  @DescribesScenario("initUpdateProcessInstanceSuspendStateBatch")
  public static ScenarioSetup initUpdateProcessInstanceSuspendStateBatch() {
    return new ScenarioSetup() {
      public void execute(ProcessEngine engine, String scenarioName) {
        List<String> processInstanceIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
          ProcessInstance processInstance = engine.getRuntimeService()
            .startProcessInstanceByKey("oneTaskProcess_710", "UpdateProcessInstanceSuspendStateBatchScenario");

          processInstanceIds.add(processInstance.getId());
        }

        engine.getRuntimeService().updateProcessInstanceSuspensionState()
          .byProcessInstanceIds(processInstanceIds)
          .suspendAsync();
      }
    };
  }
}
