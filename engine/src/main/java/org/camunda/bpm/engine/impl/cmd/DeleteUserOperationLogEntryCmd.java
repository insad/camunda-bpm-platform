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
package org.camunda.bpm.engine.impl.cmd;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.history.UserOperationLogEntry;
import org.camunda.bpm.engine.impl.cfg.CommandChecker;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

/**
 * @author Thorben Lindhauer
 *
 */
public class DeleteUserOperationLogEntryCmd implements Command<Void> {

  protected String entryId;

  public DeleteUserOperationLogEntryCmd(String entryId) {
    this.entryId = entryId;
  }

  public Void execute(CommandContext commandContext) {
    ensureNotNull(NotValidException.class, "entryId", entryId);

    UserOperationLogEntry entry = commandContext
      .getOperationLogManager()
      .findOperationLogById(entryId);

    for(CommandChecker checker : commandContext.getProcessEngineConfiguration().getCommandCheckers()) {
      checker.checkDeleteUserOperationLog(entry);
    }
    
    commandContext.getOperationLogManager().deleteOperationLogEntryById(entryId);
    return null;
  }

}
