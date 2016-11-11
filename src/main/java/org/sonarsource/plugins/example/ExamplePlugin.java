package org.sonarsource.plugins.example;

import org.sonar.api.Plugin;
import org.sonarsource.plugins.example.hooks.DisplayIssuesInScanner;
import org.sonarsource.plugins.example.hooks.DisplayQualityGateStatus;
import org.sonarsource.plugins.example.languages.FooLanguage;
import org.sonarsource.plugins.example.languages.FooQualityProfile;
import org.sonarsource.plugins.example.measures.ComputeSizeAverage;
import org.sonarsource.plugins.example.measures.ComputeSizeRating;
import org.sonarsource.plugins.example.measures.ExampleMetrics;
import org.sonarsource.plugins.example.measures.SetSizeOnFilesSensor;
import org.sonarsource.plugins.example.rules.CreateIssuesOnJavaFilesSensor;
import org.sonarsource.plugins.example.rules.FooLintIssuesLoaderSensor;
import org.sonarsource.plugins.example.rules.FooLintRulesDefinition;
import org.sonarsource.plugins.example.rules.JavaRulesDefinition;
import org.sonarsource.plugins.example.settings.ExampleProperties;
import org.sonarsource.plugins.example.settings.SayHelloFromScanner;
import org.sonarsource.plugins.example.web.ExampleFooter;
import org.sonarsource.plugins.example.web.ExampleWidget;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class ExamplePlugin implements Plugin {

  @Override
  public void define(Context context) {
    // tutorial on hooks
    // http://docs.sonarqube.org/display/DEV/Adding+Hooks
    context.addExtensions(DisplayIssuesInScanner.class, DisplayQualityGateStatus.class);

    // tutorial on languages
    context.addExtensions(FooLanguage.class, FooQualityProfile.class);

    // tutorial on measures
    context
      .addExtensions(ExampleMetrics.class, SetSizeOnFilesSensor.class, ComputeSizeAverage.class, ComputeSizeRating.class);

    // tutorial on rules
    context.addExtensions(JavaRulesDefinition.class, CreateIssuesOnJavaFilesSensor.class);
    context.addExtensions(FooLintRulesDefinition.class, FooLintIssuesLoaderSensor.class);

    // tutorial on settings
    context
      .addExtensions(ExampleProperties.definitions())
      .addExtension(SayHelloFromScanner.class);

    // tutorial on web extensions
    context.addExtensions(ExampleFooter.class, ExampleWidget.class);
  }
}
