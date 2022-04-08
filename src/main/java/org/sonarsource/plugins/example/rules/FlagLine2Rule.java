package org.sonarsource.plugins.example.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;

@Rule(key = FlagLine2Rule.RULE_KEY, name = "Example rule 2", description = "Example rule 2 description")
public class FlagLine2Rule implements FlagLineRule {
  public static final String RULE_KEY = "ExampleRule2";

  @Override
  public void execute(SensorContext sensorContext, InputFile file, RuleKey ruleKey) {
    NewIssue newIssue = sensorContext.newIssue();
    newIssue
      .forRule(ruleKey)
      .at(newIssue.newLocation()
        .on(file)
        .at(file.selectLine(2)))
      .save();
  }
}
