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

import java.util.Date;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.jobexecutor.JobHandler;
import org.camunda.bpm.engine.impl.jobexecutor.TimerChangeJobDefinitionSuspensionStateJobHandler;
import org.camunda.bpm.engine.impl.management.UpdateJobDefinitionSuspensionStateBuilderImpl;
import org.camunda.bpm.engine.impl.management.UpdateJobSuspensionStateBuilderImpl;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationManager;
import org.camunda.bpm.engine.impl.persistence.entity.JobDefinitionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.JobDefinitionManager;
import org.camunda.bpm.engine.impl.persistence.entity.JobManager;
import org.camunda.bpm.engine.impl.persistence.entity.PropertyChange;
import org.camunda.bpm.engine.impl.persistence.entity.SuspensionState;

/**
 * @author Daniel Meyer
 * @author roman.smirnov
 */
public abstract class AbstractSetJobDefinitionStateCmd extends AbstractSetStateCmd {

  protected String jobDefinitionId;
  protected String processDefinitionId;
  protected String processDefinitionKey;
  protected Date executionDate;

  protected String processDefinitionTenantId;
  protected boolean isProcessDefinitionTenantIdSet = false;

  public AbstractSetJobDefinitionStateCmd(UpdateJobDefinitionSuspensionStateBuilderImpl builder) {
    super(
        builder.isIncludeJobs(),
        builder.getExecutionDate());

    this.jobDefinitionId = builder.getJobDefinitionId();
    this.processDefinitionId = builder.getProcessDefinitionId();
    this.processDefinitionKey = builder.getProcessDefinitionKey();

    this.isProcessDefinitionTenantIdSet = builder.isProcessDefinitionTenantIdSet();
    this.processDefinitionTenantId = builder.getProcessDefinitionTenantId();
  }

  @Override
  protected void checkParameters(CommandContext commandContext) {
    if (jobDefinitionId == null && processDefinitionId == null && processDefinitionKey == null) {
      throw new ProcessEngineException("Job definition id, process definition id nor process definition key cannot be null");
    }
  }

  @Override
  protected void checkAuthorization(CommandContext commandContext) {
    AuthorizationManager authorizationManager = commandContext.getAuthorizationManager();

    if (jobDefinitionId != null) {

      JobDefinitionManager jobDefinitionManager = commandContext.getJobDefinitionManager();
      JobDefinitionEntity jobDefinition = jobDefinitionManager.findById(jobDefinitionId);

      if (jobDefinition != null) {
        String processDefinitionKey = jobDefinition.getProcessDefinitionKey();
        authorizationManager.checkUpdateProcessDefinitionByKey(processDefinitionKey);

        if (includeSubResources) {
          authorizationManager.checkUpdateProcessInstanceByProcessDefinitionKey(processDefinitionKey);
        }
      }

    } else

    if (processDefinitionId != null) {
      authorizationManager.checkUpdateProcessDefinitionById(processDefinitionId);

      if (includeSubResources) {
        authorizationManager.checkUpdateProcessInstanceByProcessDefinitionId(processDefinitionId);
      }

    } else

    if (processDefinitionKey != null) {
      authorizationManager.checkUpdateProcessDefinitionByKey(processDefinitionKey);

      if (includeSubResources) {
        authorizationManager.checkUpdateProcessInstanceByProcessDefinitionKey(processDefinitionKey);
      }
    }
  }

  @Override
  protected void updateSuspensionState(CommandContext commandContext, SuspensionState suspensionState) {
    JobDefinitionManager jobDefinitionManager = commandContext.getJobDefinitionManager();
    JobManager jobManager = commandContext.getJobManager();

    if (jobDefinitionId != null) {
      jobDefinitionManager.updateJobDefinitionSuspensionStateById(jobDefinitionId, suspensionState);

    } else if (processDefinitionId != null) {
      jobDefinitionManager.updateJobDefinitionSuspensionStateByProcessDefinitionId(processDefinitionId, suspensionState);
      jobManager.updateStartTimerJobSuspensionStateByProcessDefinitionId(processDefinitionId, suspensionState);

    } else if (processDefinitionKey != null) {

      if (!isProcessDefinitionTenantIdSet) {
        jobDefinitionManager.updateJobDefinitionSuspensionStateByProcessDefinitionKey(processDefinitionKey, suspensionState);
        jobManager.updateStartTimerJobSuspensionStateByProcessDefinitionKey(processDefinitionKey, suspensionState);

      } else {
        jobDefinitionManager.updateJobDefinitionSuspensionStateByProcessDefinitionKeyAndTenantId(processDefinitionKey, processDefinitionTenantId, suspensionState);
        jobManager.updateStartTimerJobSuspensionStateByProcessDefinitionKeyAndTenantId(processDefinitionKey, processDefinitionTenantId, suspensionState);
      }
    }
  }

  @Override
  protected String getJobHandlerConfiguration() {
    String jobConfiguration = null;

    if (jobDefinitionId != null) {
      jobConfiguration = TimerChangeJobDefinitionSuspensionStateJobHandler
          .createJobHandlerConfigurationByJobDefinitionId(jobDefinitionId, isIncludeSubResources());

    } else if (processDefinitionId != null) {
      jobConfiguration = TimerChangeJobDefinitionSuspensionStateJobHandler
          .createJobHandlerConfigurationByProcessDefinitionId(processDefinitionId, isIncludeSubResources());

    } else if (processDefinitionKey != null) {

      if (!isProcessDefinitionTenantIdSet) {
        jobConfiguration = TimerChangeJobDefinitionSuspensionStateJobHandler
            .createJobHandlerConfigurationByProcessDefinitionKey(processDefinitionKey, isIncludeSubResources());

      } else {
        jobConfiguration = TimerChangeJobDefinitionSuspensionStateJobHandler
            .createJobHandlerConfigurationByProcessDefinitionKeyAndTenantId(processDefinitionKey, processDefinitionTenantId, isIncludeSubResources());
      }
    }
    return jobConfiguration;
  }

  @Override
  protected void logUserOperation(CommandContext commandContext) {
    PropertyChange propertyChange = new PropertyChange(SUSPENSION_STATE_PROPERTY, null, getNewSuspensionState().getName());
    commandContext.getOperationLogManager().logJobDefinitionOperation(getLogEntryOperation(), jobDefinitionId,
      processDefinitionId, processDefinitionKey, propertyChange);
  }

  protected UpdateJobSuspensionStateBuilderImpl createJobCommandBuilder() {
    UpdateJobSuspensionStateBuilderImpl builder = new UpdateJobSuspensionStateBuilderImpl();

    if (jobDefinitionId != null) {
      builder.byJobDefinitionId(jobDefinitionId);

    } else if (processDefinitionId != null) {
      builder.byProcessDefinitionId(processDefinitionId);

    } else if (processDefinitionKey != null) {
      builder.byProcessDefinitionKey(processDefinitionKey);

      if (isProcessDefinitionTenantIdSet && processDefinitionTenantId != null) {
        builder.processDefinitionTenantId(processDefinitionTenantId);

      } else if (isProcessDefinitionTenantIdSet) {
        builder.processDefinitionWithoutTenantId();
      }
    }
    return builder;
  }

  /**
   * Subclasses should return the type of the {@link JobHandler} here. it will be used when
   * the user provides an execution date on which the actual state change will happen.
   */
  @Override
  protected abstract String getDelayedExecutionJobHandlerType();

  @Override
  protected AbstractSetStateCmd getNextCommand() {
    UpdateJobSuspensionStateBuilderImpl jobCommandBuilder = createJobCommandBuilder();

    return getNextCommand(jobCommandBuilder);
  }

  protected abstract AbstractSetJobStateCmd getNextCommand(UpdateJobSuspensionStateBuilderImpl jobCommandBuilder);

}
