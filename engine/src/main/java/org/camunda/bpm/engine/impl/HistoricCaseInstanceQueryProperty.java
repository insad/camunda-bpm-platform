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
package org.camunda.bpm.engine.impl;

import org.camunda.bpm.engine.query.QueryProperty;


/**
 * Contains the possible properties which can be used in a {@link HistoricCaseInstanceQueryProperty}.
 *
 * @author Sebastian Menski
 */
public interface HistoricCaseInstanceQueryProperty {

  public static final QueryProperty PROCESS_INSTANCE_ID_ = new QueryPropertyImpl("CASE_INST_ID_");
  public static final QueryProperty PROCESS_DEFINITION_ID = new QueryPropertyImpl("CASE_DEF_ID_");
  public static final QueryProperty BUSINESS_KEY = new QueryPropertyImpl("BUSINESS_KEY_");
  public static final QueryProperty CREATE_TIME = new QueryPropertyImpl("CREATE_TIME_");
  public static final QueryProperty CLOSE_TIME = new QueryPropertyImpl("CLOSE_TIME_");
  public static final QueryProperty DURATION = new QueryPropertyImpl("DURATION_");
  public static final QueryProperty TENANT_ID = new QueryPropertyImpl("TENANT_ID_");

}
