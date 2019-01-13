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
package org.camunda.bpm.qa.rolling.update.scenarios.mulltiInstance;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.qa.upgrade.DescribesScenario;
import org.camunda.bpm.qa.upgrade.ScenarioSetup;
import org.camunda.bpm.qa.upgrade.Times;

/**
 *
 * @author Christopher Zell <christopher.zell@camunda.com>
 */
public class ProcessWithMultiInstanceCallActivityScenario {

  public static final String PROCESS_DEF_KEY = "processWithMultiInstanceCallActivity";

  @Deployment
  public static String deploy() {
    return "org/camunda/bpm/qa/rolling/update/processWithMultiInstanceCallActivity.bpmn20.xml";
  }

  @Deployment
  public static String deploySubProcess() {
    return "org/camunda/bpm/qa/rolling/update/simpleSubProcess.bpmn20.xml";
  }

  @DescribesScenario("init")
  @Times(1)
  public static ScenarioSetup startProcess() {
    return new ScenarioSetup() {
      public void execute(ProcessEngine engine, String scenarioName) {
        engine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEF_KEY, scenarioName);
      }
    };
  }

  @DescribesScenario("init.complete.one")
  @Times(1)
  public static ScenarioSetup startProcessAndCompleteFirstTask() {
    return new ScenarioSetup() {
      public void execute(ProcessEngine engine, String scenarioName) {
        ProcessInstance procInst = engine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEF_KEY, scenarioName);
        Task task = engine.getTaskService().createTaskQuery().processInstanceId(procInst.getId()).singleResult();
        engine.getTaskService().complete(task.getId());
      }
    };
  }


}
