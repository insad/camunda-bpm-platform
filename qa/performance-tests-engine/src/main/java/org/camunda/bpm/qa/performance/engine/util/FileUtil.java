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
package org.camunda.bpm.qa.performance.engine.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.camunda.bpm.engine.impl.util.IoUtil;
import org.camunda.bpm.qa.performance.engine.framework.PerfTestException;

/**
 * @author Daniel Meyer, Ingo Richtsmeier
 *
 */
public class FileUtil {

  public static void writeStringToFile(String value, String filePath) {
    writeStringToFile(value, filePath, true);
  }

  public static void writeStringToFile(String value, String filePath, boolean deleteFile) {

    BufferedOutputStream outputStream = null;
    try {
      File file = new File(filePath);
      if (file.exists() && deleteFile) {
        file.delete();
      }

      outputStream = new BufferedOutputStream(new FileOutputStream(file, true));
      outputStream.write(value.getBytes());
      outputStream.flush();

    } catch (Exception e) {
      throw new PerfTestException("Could not write report to file", e);
    } finally {
      IoUtil.closeSilently(outputStream);
    }

  }
  
  public static void appendStringToFile(String value, String filePath) {
    writeStringToFile(value, filePath, false);
  }

}
