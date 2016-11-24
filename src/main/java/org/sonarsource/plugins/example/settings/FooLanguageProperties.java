package org.sonarsource.plugins.example.settings;

import static java.util.Arrays.asList;

import java.util.List;

import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

public class FooLanguageProperties {

  public static final String FILE_SUFFIXES_KEY = "sonar.foo.file.suffixes";
  public static final String FILE_SUFFIXES_DEFAULT_VALUE = ".foo";

  private FooLanguageProperties() {
    // only statics
  }

  public static List<PropertyDefinition> getProperties() {
    return asList(PropertyDefinition.builder(FILE_SUFFIXES_KEY)
      .defaultValue(FILE_SUFFIXES_DEFAULT_VALUE)
      .category("Foo")
      .name("File Suffixes")
      .description("Comma-separated list of suffixes for files to analyze.")
      .onQualifiers(Qualifiers.PROJECT)
      .build());
  }

}
