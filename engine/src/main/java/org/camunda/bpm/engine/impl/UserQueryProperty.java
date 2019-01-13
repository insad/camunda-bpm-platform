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

import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.query.QueryProperty;



/**
 * Contains the possible properties that can be used by the {@link UserQuery}.
 *
 * @author Joram Barrez
 */
public interface UserQueryProperty {

  public static final QueryProperty USER_ID = new QueryPropertyImpl("ID_");
  public static final QueryProperty FIRST_NAME = new QueryPropertyImpl("FIRST_");
  public static final QueryProperty LAST_NAME = new QueryPropertyImpl("LAST_");
  public static final QueryProperty EMAIL = new QueryPropertyImpl("EMAIL_");

}
