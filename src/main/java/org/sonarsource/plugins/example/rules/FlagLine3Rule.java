package org.sonarsource.plugins.example.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;

@Rule(key = FlagLine3Rule.RULE_KEY, name = "Example rule 3", description = "Example rule 3 description")
public class FlagLine3Rule implements FlagLineRule {
  public static final String RULE_KEY = "ExampleRule3";

  @Override
  public void execute(SensorContext sensorContext, InputFile file, RuleKey ruleKey) {
    NewIssue newIssue = sensorContext.newIssue();
    newIssue
      .forRule(ruleKey)
      .at(newIssue.newLocation()
        .on(file)
        .at(file.selectLine(3)))
      .save();
  }
}
