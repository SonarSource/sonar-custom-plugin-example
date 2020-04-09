/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2020 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.plugins.example.rules;

import org.sonar.api.rule.RuleKey;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rule.Severity;
import org.sonar.api.server.rule.RulesDefinition;

public class JavaRulesDefinition implements RulesDefinition {

  public static final String REPOSITORY = "java-example";
  public static final String JAVA_LANGUAGE = "java";
  public static final RuleKey RULE_ON_LINE_1 = RuleKey.of(REPOSITORY, "line1");

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPOSITORY, JAVA_LANGUAGE).setName("My Custom Java Analyzer");

    NewRule x1Rule = repository.createRule(RULE_ON_LINE_1.rule())
      .setName("Stupid rule")
      .setHtmlDescription("Generates an issue on every line 1 of Java files")

      // optional tags
      .setTags("style", "stupid")

      // optional status. Default value is READY.
      .setStatus(RuleStatus.BETA)

      // default severity when the rule is activated on a Quality profile. Default value is MAJOR.
      .setSeverity(Severity.MINOR);

    x1Rule.setDebtRemediationFunction(x1Rule.debtRemediationFunctions().linearWithOffset("1h", "30min"));

    // don't forget to call done() to finalize the definition
    repository.done();
  }
}
