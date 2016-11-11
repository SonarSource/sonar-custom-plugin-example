package org.sonarsource.plugins.example.settings;

import static java.util.Arrays.asList;

import java.util.List;

import org.sonar.api.config.PropertyDefinition;

public class ExampleProperties {

  public static final String HELLO_KEY = "sonar.example.hello";
  public static final String CATEGORY = "Example";

  private ExampleProperties() {
    // only statics
  }

  public static List<PropertyDefinition> definitions() {
    return asList(
      PropertyDefinition.builder(HELLO_KEY)
        .name("Hello")
        .description("Say Hello")
        .defaultValue(String.valueOf(false))
        .category(CATEGORY)
        .build()
      );
  }
}
