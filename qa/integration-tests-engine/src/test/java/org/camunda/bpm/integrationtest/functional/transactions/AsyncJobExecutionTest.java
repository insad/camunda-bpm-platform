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
package org.camunda.bpm.integrationtest.functional.transactions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import javax.inject.Inject;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.integrationtest.functional.transactions.beans.FailingTransactionListenerDelegate;
import org.camunda.bpm.integrationtest.functional.transactions.beans.GetVersionInfoDelegate;
import org.camunda.bpm.integrationtest.functional.transactions.beans.TransactionRollbackDelegate;
import org.camunda.bpm.integrationtest.functional.transactions.beans.UpdateRouterConfiguration;
import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AsyncJobExecutionTest extends AbstractFoxPlatformIntegrationTest {

  @Deployment
  public static WebArchive processArchive() {
    return initWebArchiveDeployment()
            .addClass(GetVersionInfoDelegate.class)
            .addClass(UpdateRouterConfiguration.class)
            .addClass(TransactionRollbackDelegate.class)
            .addClass(FailingTransactionListenerDelegate.class)
            .addAsResource("org/camunda/bpm/integrationtest/functional/transactions/AsyncJobExecutionTest.testAsyncServiceTasks.bpmn20.xml")
            .addAsResource("org/camunda/bpm/integrationtest/functional/transactions/AsyncJobExecutionTest.transactionRollbackInServiceTask.bpmn20.xml")
            .addAsResource("org/camunda/bpm/integrationtest/functional/transactions/AsyncJobExecutionTest.transactionRollbackInServiceTaskWithCustomRetryCycle.bpmn20.xml")
            .addAsResource("org/camunda/bpm/integrationtest/functional/transactions/AsyncJobExecutionTest.failingTransactionListener.bpmn20.xml")
            .addAsWebInfResource("persistence.xml", "classes/META-INF/persistence.xml");
  }

  @Inject
  private RuntimeService runtimeService;

  @After
  public void cleanUp() {
    for (ProcessInstance processInstance : runtimeService.createProcessInstanceQuery().list()) {
      runtimeService.deleteProcessInstance(processInstance.getId(), "test ended", true);
    }
  }

  @Test
  public void testAsyncServiceTasks() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("serialnumber", "23");
    runtimeService.startProcessInstanceByKey("configure-router", variables);

    waitForJobExecutorToProcessAllJobs();
  }

  @Test
  public void testTransactionRollbackInServiceTask() throws Exception {

    runtimeService.startProcessInstanceByKey("txRollbackServiceTask");

    waitForJobExecutorToProcessAllJobs(10000);

    Job job = managementService.createJobQuery().singleResult();

    assertNotNull(job);
    assertEquals(0, job.getRetries());
    assertNotNull(job.getExceptionMessage());
    assertNotNull(managementService.getJobExceptionStacktrace(job.getId()));
  }

  @Test
  public void testTransactionRollbackInServiceTaskWithCustomRetryCycle() throws Exception {

    runtimeService.startProcessInstanceByKey("txRollbackServiceTaskWithCustomRetryCycle");

    waitForJobExecutorToProcessAllJobs(10000);

    Job job = managementService.createJobQuery().singleResult();

    assertNotNull(job);
    assertEquals(0, job.getRetries());
    assertNotNull(job.getExceptionMessage());
    assertNotNull(managementService.getJobExceptionStacktrace(job.getId()));
  }

  @Test
  public void testFailingTransactionListener() throws Exception {

    runtimeService.startProcessInstanceByKey("failingTransactionListener");

    waitForJobExecutorToProcessAllJobs(10000);

    Job job = managementService.createJobQuery().singleResult();

    assertNotNull(job);
    assertEquals(0, job.getRetries());
    assertNotNull(job.getExceptionMessage());
    assertNotNull(managementService.getJobExceptionStacktrace(job.getId()));
  }

}
