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
package org.camunda.bpm.integrationtest.deployment.jar;

import org.camunda.bpm.application.impl.ejb.DefaultEjbProcessApplication;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test only runs on JBoss AS, as for all other servers, Arquillian wraps the jar in a war file
 * to pack the test runtime. However, we want to deploy a plain jar. This is supported by JBoss-exclusive
 * protocol 'jmx-as7'.
 * 
 * @author Thorben Lindhauer
 *
 */
@RunWith(Arquillian.class)
public class TestJarDeployment extends AbstractFoxPlatformIntegrationTest {
  
  @Deployment
  @OverProtocol("jmx-as7")
  public static JavaArchive processArchive() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(AbstractFoxPlatformIntegrationTest.class)
      .addClass(DefaultEjbProcessApplication.class)
      .addAsResource("META-INF/processes.xml", "META-INF/processes.xml")
      .addAsResource("org/camunda/bpm/integrationtest/testDeployProcessArchive.bpmn20.xml");
  }
  
  @Test
  public void testDeployProcessArchive() {
    Assert.assertNotNull(processEngine);
    RepositoryService repositoryService = processEngine.getRepositoryService();
    long count = repositoryService.createProcessDefinitionQuery()
      .processDefinitionKey("testDeployProcessArchive")
      .count();
    
    Assert.assertEquals(1, count);
  }

}
