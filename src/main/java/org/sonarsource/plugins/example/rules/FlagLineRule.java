package org.sonarsource.plugins.example.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.rule.RuleKey;

public interface FlagLineRule {

  void execute(SensorContext sensorContext, InputFile file, RuleKey ruleKey);
}
