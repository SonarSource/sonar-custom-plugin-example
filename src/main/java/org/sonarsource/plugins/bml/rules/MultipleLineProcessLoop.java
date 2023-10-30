package org.sonarsource.plugins.bml.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;

@Rule(key = MultipleLineProcessLoop.RULE_KEY, name = "Multiple Line Processing Loop ", description = "File shall not have multiple loops")
public class MultipleLineProcessLoop implements FlagLineRule {
  public static final String RULE_KEY = "MultipleLineProcessingLoop";

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
