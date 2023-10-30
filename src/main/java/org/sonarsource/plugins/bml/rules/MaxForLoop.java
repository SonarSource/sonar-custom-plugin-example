package org.sonarsource.plugins.bml.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;

@Rule(key = MaxForLoop.RULE_KEY, name = "Maximum For Loop", description = "File shall not have a maximum loops defined in the configuration")
public class MaxForLoop implements FlagLineRule {
  public static final String RULE_KEY = "MaximumForLoop";

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
