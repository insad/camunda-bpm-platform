/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmd;

import org.camunda.bpm.engine.history.UserOperationLogEntry;
import org.camunda.bpm.engine.impl.management.UpdateJobSuspensionStateBuilderImpl;
import org.camunda.bpm.engine.impl.persistence.entity.SuspensionState;

/**
 *
 * @author Daniel Meyer
 */
public class ActivateProcessInstanceCmd extends AbstractSetProcessInstanceStateCmd {

  public ActivateProcessInstanceCmd(String executionId, String processDefinitionId, String processDefinitionKey) {
    super(executionId, processDefinitionId, processDefinitionKey, false, null);
  }

  public ActivateProcessInstanceCmd(String executionId, String processDefinitionId, String processDefinitionKey, boolean isTenantIdSet, String tenantId) {
    super(executionId, processDefinitionId, processDefinitionKey, isTenantIdSet, tenantId);
  }

  @Override
  protected SuspensionState getNewSuspensionState() {
    return SuspensionState.ACTIVE;
  }

  @Override
  protected ActivateJobCmd getNextCommand(UpdateJobSuspensionStateBuilderImpl jobCommandBuilder) {
    return new ActivateJobCmd(jobCommandBuilder);
  }

  @Override
  protected String getLogEntryOperation() {
    return UserOperationLogEntry.OPERATION_TYPE_ACTIVATE;
  }

}
