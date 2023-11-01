package org.sonarsource.plugins.bml.rules;

import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonarsource.plugins.bml.languages.BMLLanguage;

public class FlagLineSensor implements Sensor {

  private final Checks<FlagLineRule> checks;

  public FlagLineSensor(CheckFactory checkFactory) {
    checks = checkFactory.create(FlagRuleDefinition.REPO_KEY);
    checks.addAnnotatedChecks(AllignmentChecker.class,CommentChecker.class,MaxForLoop.class,MultipleLineProcessLoop.class,NoReturnStatement.class,StringConcatenation.class);
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name(CommentChecker.RULE_KEY + "sensor");
    descriptor.onlyOnLanguages(BMLLanguage.KEY);
    descriptor.createIssuesForRuleRepository(FlagRuleDefinition.REPO_KEY);
  }

  @Override
  public void execute(SensorContext context) {
    FilePredicates p = context.fileSystem().predicates();
    for (InputFile inputFile : context.fileSystem().inputFiles(p.hasLanguages(BMLLanguage.KEY))) {
      checks.all().forEach(check -> check.execute(context, inputFile, checks.ruleKey(check)));
    }
  }
}
