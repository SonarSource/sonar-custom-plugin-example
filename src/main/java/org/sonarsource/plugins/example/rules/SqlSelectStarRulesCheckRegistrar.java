
package org.sonarsource.plugins.example.rules;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.Collections;
import java.util.List;

public class SqlSelectStarRulesCheckRegistrar implements CheckRegistrar {
    @Override
    public void register(RegistrarContext registrarContext) {
        registrarContext.registerClassesForRepository(
                SqlSelectStarRulesDefinition.REPOSITORY_KEY,
                SqlSelectStarRulesRegistrar.checkClasses(),
                SqlSelectStarRulesRegistrar.testCheckClasses()
        );
    }

    public static class SqlSelectStarRulesRegistrar {
        public static List<Class<? extends JavaCheck>> checkClasses() {
            return SqlSelectStarRulesDefinition.getChecks();
        }

        public static List<Class<? extends JavaCheck>> testCheckClasses() {
            return Collections.emptyList();
        }
    }
}
