package org.sonarsource.plugins.example.rules;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.Java;

import java.util.Collections;
import java.util.List;

public class SqlSelectStarRulesDefinition implements RulesDefinition {
    public static final String REPOSITORY_KEY = "sql-select-star";
    public static final String REPOSITORY_NAME = "SQL Select Star Rules";

    @Override
    public void define(Context context) {
        NewRepository repository = context
                .createRepository(REPOSITORY_KEY, Java.KEY)
                .setName(REPOSITORY_NAME);

        // Register the rule
        org.sonar.api.server.rule.RulesDefinitionAnnotationLoader annotationLoader =
                new org.sonar.api.server.rule.RulesDefinitionAnnotationLoader();

        // Load rule(s) from annotation
        annotationLoader.load(repository, SqlSelectStarRulesCheckRegistrar.SqlSelectStarRulesRegistrar.checkClasses().toArray(new Class[0]));

        repository.done();
    }

    public static List<Class<? extends JavaCheck>> getChecks() {
        return Collections.singletonList(NoSelectStarRule.class);
    }
}
