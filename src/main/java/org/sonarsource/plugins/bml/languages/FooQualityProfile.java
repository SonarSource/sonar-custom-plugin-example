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
package org.sonarsource.plugins.bml.languages;

import static org.sonarsource.plugins.bml.rules.FlagRuleDefinition.REPO_KEY;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonarsource.plugins.bml.rules.AllignmentChecker;
import org.sonarsource.plugins.bml.rules.CommentChecker;
import org.sonarsource.plugins.bml.rules.MaxForLoop;
import org.sonarsource.plugins.bml.rules.MultipleLineProcessLoop;
import org.sonarsource.plugins.bml.rules.NoReturnStatement;
import org.sonarsource.plugins.bml.rules.StringConcatenation;

/**
 * Default, BuiltIn Quality Profile for the projects having files of the language "bml"
 */
public final class FooQualityProfile implements BuiltInQualityProfilesDefinition {

  @Override
  public void define(Context context) {
    NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("FooLint Rules", BMLLanguage.KEY);
    profile.setDefault(true);

    NewBuiltInActiveRule rule1 = profile.activateRule(REPO_KEY, CommentChecker.RULE_KEY);
    rule1.overrideSeverity("BLOCKER");
    NewBuiltInActiveRule rule2 = profile.activateRule(REPO_KEY, MaxForLoop.RULE_KEY);
    rule2.overrideSeverity("MAJOR");
	NewBuiltInActiveRule rule3 = profile.activateRule(REPO_KEY, AllignmentChecker.RULE_KEY);
	rule3.overrideSeverity("CRITICAL");
	NewBuiltInActiveRule rule4 = profile.activateRule(REPO_KEY,	MultipleLineProcessLoop.RULE_KEY);
	rule4.overrideSeverity("MAJOR");
	NewBuiltInActiveRule rule5 = profile.activateRule(REPO_KEY,	NoReturnStatement.RULE_KEY);
	rule5.overrideSeverity("CRITICAL");
	NewBuiltInActiveRule rule6 = profile.activateRule(REPO_KEY,	StringConcatenation.RULE_KEY);
	rule6.overrideSeverity("BLOCKER");
    profile.done();
  }

}
