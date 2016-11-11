package org.sonarsource.plugins.example.rules;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;

/**
 * Generates issues on all java files at line 1. This rule
 * must be activated in the Quality profile.
 */
public class CreateIssuesOnJavaFilesSensor implements Sensor {

  private static final double ARBITRARY_GAP = 2.0;
  private static final int LINE_1 = 1;

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name("Add issues on line 1 of all Java files");

    // optimisation to disable execution of sensor if project does
    // not contain Java files or if the example rule is not activated
    // in the Quality profile
    descriptor.onlyOnLanguage("java");
    descriptor.createIssuesForRuleRepositories(JavaRulesDefinition.REPOSITORY);
  }

  @Override
  public void execute(SensorContext context) {
    FileSystem fs = context.fileSystem();
    Iterable<InputFile> javaFiles = fs.inputFiles(fs.predicates().hasLanguage("java"));
    for (InputFile javaFile : javaFiles) {
      // no need to define the severity as it is automatically set according
      // to the configured Quality profile
      NewIssue newIssue = context.newIssue()
        .forRule(JavaRulesDefinition.RULE_ON_LINE_1)

        // gap is used to estimate the remediation cost to fix the debt
        .gap(ARBITRARY_GAP);

      NewIssueLocation primaryLocation = newIssue.newLocation()
        .on(javaFile)
        .at(javaFile.selectLine(LINE_1))
        .message("You can't do anything. This is first line!");
      newIssue.at(primaryLocation);
      newIssue.save();
    }
  }
}
