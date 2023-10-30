package org.sonarsource.plugins.bml.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;

@Rule(key = NoReturnStatement.RULE_KEY, name = "No Return Statement ", description = "File shall contain return statement")
public class NoReturnStatement implements FlagLineRule {
  public static final String RULE_KEY = "NoReturnStatement";

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
