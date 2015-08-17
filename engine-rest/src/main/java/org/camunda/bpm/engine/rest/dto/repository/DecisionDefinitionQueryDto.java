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
package org.camunda.bpm.engine.rest.dto.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.DecisionDefinitionQuery;
import org.camunda.bpm.engine.rest.dto.AbstractQueryDto;
import org.camunda.bpm.engine.rest.dto.CamundaQueryParam;
import org.camunda.bpm.engine.rest.dto.converter.BooleanConverter;
import org.camunda.bpm.engine.rest.dto.converter.IntegerConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DecisionDefinitionQueryDto extends AbstractQueryDto<DecisionDefinitionQuery> {

  private static final String SORT_BY_ID_VALUE = "id";
  private static final String SORT_BY_KEY_VALUE = "key";
  private static final String SORT_BY_NAME_VALUE = "name";
  private static final String SORT_BY_VERSION_VALUE = "version";
  private static final String SORT_BY_DEPLOYMENT_ID_VALUE = "deploymentId";
  private static final String SORT_BY_CATEGORY_VALUE = "category";

  private static final List<String> VALID_SORT_BY_VALUES;

  static {
    VALID_SORT_BY_VALUES = new ArrayList<String>();

    VALID_SORT_BY_VALUES.add(SORT_BY_CATEGORY_VALUE);
    VALID_SORT_BY_VALUES.add(SORT_BY_KEY_VALUE);
    VALID_SORT_BY_VALUES.add(SORT_BY_ID_VALUE);
    VALID_SORT_BY_VALUES.add(SORT_BY_NAME_VALUE);
    VALID_SORT_BY_VALUES.add(SORT_BY_VERSION_VALUE);
    VALID_SORT_BY_VALUES.add(SORT_BY_DEPLOYMENT_ID_VALUE);

  }

  protected String decisionDefinitionId;
  protected String category;
  protected String categoryLike;
  protected String name;
  protected String nameLike;
  protected String deploymentId;
  protected String key;
  protected String keyLike;
  protected String resourceName;
  protected String resourceNameLike;
  protected Integer version;
  protected Boolean latestVersion;

  public DecisionDefinitionQueryDto() {}

  public DecisionDefinitionQueryDto(ObjectMapper objectMapper, MultivaluedMap<String, String> queryParameters) {
    super(objectMapper, queryParameters);
  }

  @CamundaQueryParam("decisionDefinitionId")
  public void setDecisionDefinitionId(String decisionDefinitionId) {
    this.decisionDefinitionId = decisionDefinitionId;
  }

  @CamundaQueryParam("category")
  public void setCategory(String category) {
    this.category = category;
  }

  @CamundaQueryParam("categoryLike")
  public void setCategoryLike(String categoryLike) {
    this.categoryLike = categoryLike;
  }

  @CamundaQueryParam("name")
  public void setName(String name) {
    this.name = name;
  }

  @CamundaQueryParam("nameLike")
  public void setNameLike(String nameLike) {
    this.nameLike = nameLike;
  }

  @CamundaQueryParam("deploymentId")
  public void setDeploymentId(String deploymentId) {
    this.deploymentId = deploymentId;
  }

  @CamundaQueryParam("key")
  public void setKey(String key) {
    this.key = key;
  }

  @CamundaQueryParam("keyLike")
  public void setKeyLike(String keyLike) {
    this.keyLike = keyLike;
  }

  @CamundaQueryParam("resourceName")
  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  @CamundaQueryParam("resourceNameLike")
  public void setResourceNameLike(String resourceNameLike) {
    this.resourceNameLike = resourceNameLike;
  }

  @CamundaQueryParam(value = "version", converter = IntegerConverter.class)
  public void setVersion(Integer version) {
    this.version = version;
  }

  @CamundaQueryParam(value = "latestVersion", converter = BooleanConverter.class)
  public void setLatestVersion(Boolean latestVersion) {
    this.latestVersion = latestVersion;
  }

  protected boolean isValidSortByValue(String value) {
    return VALID_SORT_BY_VALUES.contains(value);
  }

  protected DecisionDefinitionQuery createNewQuery(ProcessEngine engine) {
    return engine.getRepositoryService().createDecisionDefinitionQuery();
  }

  protected void applyFilters(DecisionDefinitionQuery query) {
    if (decisionDefinitionId != null) {
      query.decisionDefinitionId(decisionDefinitionId);
    }
    if (category != null) {
      query.decisionDefinitionCategory(category);
    }
    if (categoryLike != null) {
      query.decisionDefinitionCategoryLike(categoryLike);
    }
    if (name != null) {
      query.decisionDefinitionName(name);
    }
    if (nameLike != null) {
      query.decisionDefinitionNameLike(nameLike);
    }
    if (deploymentId != null) {
      query.deploymentId(deploymentId);
    }
    if (key != null) {
      query.decisionDefinitionKey(key);
    }
    if (keyLike != null) {
      query.decisionDefinitionKeyLike(keyLike);
    }
    if (resourceName != null) {
      query.decisionDefinitionResourceName(resourceName);
    }
    if (resourceNameLike != null) {
      query.decisionDefinitionResourceNameLike(resourceNameLike);
    }
    if (version != null) {
      query.decisionDefinitionVersion(version);
    }
    if (latestVersion != null && latestVersion) {
      query.latestVersion();
    }
  }

  protected void applySortBy(DecisionDefinitionQuery query, String sortBy, Map<String, Object> parameters, ProcessEngine engine) {
    if (sortBy.equals(SORT_BY_CATEGORY_VALUE)) {
      query.orderByDecisionDefinitionCategory();
    } else if (sortBy.equals(SORT_BY_KEY_VALUE)) {
      query.orderByDecisionDefinitionKey();
    } else if (sortBy.equals(SORT_BY_ID_VALUE)) {
      query.orderByDecisionDefinitionId();
    } else if (sortBy.equals(SORT_BY_VERSION_VALUE)) {
      query.orderByDecisionDefinitionVersion();
    } else if (sortBy.equals(SORT_BY_NAME_VALUE)) {
      query.orderByDecisionDefinitionName();
    } else if (sortBy.equals(SORT_BY_DEPLOYMENT_ID_VALUE)) {
      query.orderByDeploymentId();
    }
  }

}