package org.sonarsource.plugins.bml.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;

@Rule(key = AllignmentChecker.RULE_KEY, name = "Allignment Checker", description = "File shall be alligned")
public class AllignmentChecker implements FlagLineRule {
  public static final String RULE_KEY = "AllignmentChecker";

  @Override
  public void execute(SensorContext sensorContext, InputFile file, RuleKey ruleKey) {
	NewIssue newIssue = sensorContext.newIssue();
    newIssue
      .forRule(ruleKey)
      .at(newIssue.newLocation()
        .on(file)
        .at(file.selectLine(1)))
      .save();
  }
}
