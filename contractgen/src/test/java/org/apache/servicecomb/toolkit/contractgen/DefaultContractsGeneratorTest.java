/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.toolkit.contractgen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.servicecomb.toolkit.ContractsGenerator;
import org.apache.servicecomb.toolkit.GeneratorFactory;
import org.apache.servicecomb.toolkit.common.ClassMaker;
import org.junit.Rule;
import org.junit.Test;

public class DefaultContractsGeneratorTest {

  @Rule
  public TestResources resources = new TestResources();

  private static final String TEST_PROJECT = "demo";

  @Test
  public void testCanProcess() {
    assertTrue(new DefaultContractsGenerator().canProcess("default"));
    assertFalse(new DefaultContractsGenerator().canProcess("others"));
  }

  @Test
  public void testConfigure() throws DependencyResolutionRequiredException {
    Map<String, Object> config = new HashMap<>();

    MavenProject project = new MavenProject();
    config.put("classpathUrls", project.getRuntimeClasspathElements());
    config.put("outputDir", "target");
    config.put("contractfileType", "yaml");
  }

  @Test
  public void testGenerate()
      throws DependencyResolutionRequiredException, IOException, TimeoutException, InterruptedException {

    File demoPath = this.resources.getBasedir(TEST_PROJECT);
    ClassMaker.compile(demoPath.getCanonicalPath());
    MavenProject project = mock(MavenProject.class);

    List<String> runtimeUrlPath = new ArrayList<>();
    runtimeUrlPath.add(demoPath + File.separator + "target/classes");

    Map<String, Object> config = new HashMap<>();
    given(project.getRuntimeClasspathElements()).willReturn(runtimeUrlPath);
    config.put("classpathUrls", project.getRuntimeClasspathElements());
    config.put("outputDir", "target");
    config.put("contractfileType", "yaml");

    DefaultContractsGenerator defaultContractsGenerator = new DefaultContractsGenerator();
    defaultContractsGenerator.configure(config);
    assertTrue(defaultContractsGenerator.generate());
  }

  @Test
  public void testCheckConfig() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException,
      DependencyResolutionRequiredException {
    DefaultContractsGenerator defaultContractsGenerator = new DefaultContractsGenerator();
    Method method = defaultContractsGenerator.getClass().getDeclaredMethod("checkConfig", new Class[] {});
    method.setAccessible(true);

    defaultContractsGenerator.configure(null);
    assertFalse((boolean) method.invoke(defaultContractsGenerator, new Object[] {}));

    Map<String, Object> config = new HashMap<>();
    config.put("classpathUrls", null);
    defaultContractsGenerator.configure(config);
    assertFalse((boolean) method.invoke(defaultContractsGenerator, new Object[] {}));

    MavenProject project = new MavenProject();
    config.put("classpathUrls", project.getRuntimeClasspathElements());
    defaultContractsGenerator.configure(config);
    assertTrue((boolean) method.invoke(defaultContractsGenerator, new Object[] {}));
  }

  @Test
  public void testPrivateCanProcess() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    DefaultContractsGenerator defaultContractsGenerator = new DefaultContractsGenerator();
    Method method = defaultContractsGenerator.getClass().getDeclaredMethod("canProcess", new Class[] {Class.class});
    method.setAccessible(true);

    assertFalse((boolean) method.invoke(defaultContractsGenerator, new Object[] {null}));
  }

  @Test
  public void testgetAllClass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    DefaultContractsGenerator defaultContractsGenerator = new DefaultContractsGenerator();
    Method method = defaultContractsGenerator.getClass()
        .getDeclaredMethod("getAllClass", new Class[] {ClassLoader.class});
    method.setAccessible(true);

    try {
      method.invoke(defaultContractsGenerator, new Object[] {null});
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void getContractsGeneratorInstance() {

    ContractsGenerator defaultGenerator = GeneratorFactory.getGenerator(ContractsGenerator.class, "default");
    assertNotNull(defaultGenerator);

    ContractsGenerator unknownGenerator = GeneratorFactory.getGenerator(ContractsGenerator.class, "unknown");
    assertNull(unknownGenerator);
  }
}