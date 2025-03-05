package org.sonarsource.plugins.example.rules;


import org.sonar.api.Plugin;
import org.sonar.api.SonarProduct;

public class SqlSelectStarRulesPlugin implements Plugin {
    @Override
    public void define(Context context) {
        // Register the rule repository only for SonarQube (not for SonarLint)
        if (context.getRuntime().getProduct() != SonarProduct.SONARLINT) {
            context.addExtension(SqlSelectStarRulesDefinition.class);
            context.addExtension(SqlSelectStarRulesCheckRegistrar.class);
        }
    }
}
