import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonarsource.plugins.example.rules.NoSelectStarRule;

class NoSelectStarRuleTest {

    @Test
    void test() {
        CheckVerifier.newVerifier()
                .onFile("src/test/java/NoSelectStarRuleCheck.java")
                .withCheck(new NoSelectStarRule())
                .verifyIssues();
    }
}
