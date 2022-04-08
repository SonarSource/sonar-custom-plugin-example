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

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonarsource.plugins.example.languages.FooLanguage;

public final class FlagRuleDefinition implements RulesDefinition {

  static final String KEY = "flagLine";
  public static final String REPO_KEY = FooLanguage.KEY + "-" + KEY;
  private static final String REPO_NAME = FooLanguage.KEY + "- " + KEY + " repo";

  @Override
  public void define(Context context) {
    NewRepository repository = context.createRepository(REPO_KEY, FooLanguage.KEY).setName(REPO_NAME);

    RulesDefinitionAnnotationLoader rulesDefinitionAnnotationLoader = new RulesDefinitionAnnotationLoader();
    rulesDefinitionAnnotationLoader.load(repository, FlagLine1Rule.class, FlagLine2Rule.class, FlagLine3Rule.class);

    repository.done();
  }

}
