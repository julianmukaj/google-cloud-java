/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gcloud.resourcemanager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gcloud.BaseSerializationTest;
import com.google.gcloud.Identity;
import com.google.gcloud.PageImpl;

import java.io.Serializable;
import java.util.Collections;

public class SerializationTest extends BaseSerializationTest {

private static final ResourceManager RESOURCE_MANAGER =
      ResourceManagerOptions.defaultInstance().service();
  private static final ProjectInfo PARTIAL_PROJECT_INFO = ProjectInfo.builder("id1").build();
  private static final ProjectInfo FULL_PROJECT_INFO = ProjectInfo.builder("id")
      .name("name")
      .labels(ImmutableMap.of("key", "value"))
      .projectNumber(123L)
      .state(ProjectInfo.State.ACTIVE)
      .createTimeMillis(1234L)
      .build();
  private static final Project PROJECT =
      new Project(RESOURCE_MANAGER, new ProjectInfo.BuilderImpl(FULL_PROJECT_INFO));
  private static final PageImpl<Project> PAGE_RESULT =
      new PageImpl<>(null, "c", Collections.singletonList(PROJECT));
  private static final ResourceManager.ProjectGetOption PROJECT_GET_OPTION =
      ResourceManager.ProjectGetOption.fields(ResourceManager.ProjectField.NAME);
  private static final ResourceManager.ProjectListOption PROJECT_LIST_OPTION =
      ResourceManager.ProjectListOption.filter("name:*");
  private static final Policy POLICY = Policy.builder()
      .addBinding(Policy.Role.viewer(), ImmutableSet.of(Identity.user("abc@gmail.com")))
      .build();

  @Override
  public Serializable[] serializableObjects() {
    ResourceManagerOptions options = ResourceManagerOptions.builder().build();
    ResourceManagerOptions otherOptions = options.toBuilder()
        .projectId("some-unnecessary-project-ID")
        .build();
    return new Serializable[]{PARTIAL_PROJECT_INFO, FULL_PROJECT_INFO, PROJECT, PAGE_RESULT,
        PROJECT_GET_OPTION, PROJECT_LIST_OPTION, POLICY, options, otherOptions};
  }
}
