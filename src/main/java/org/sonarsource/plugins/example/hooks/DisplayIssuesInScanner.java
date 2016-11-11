package org.sonarsource.plugins.example.hooks;

import org.sonar.api.batch.postjob.PostJob;
import org.sonar.api.batch.postjob.PostJobContext;
import org.sonar.api.batch.postjob.PostJobDescriptor;
import org.sonar.api.batch.postjob.issue.PostJobIssue;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class DisplayIssuesInScanner implements PostJob {

  private static final Logger LOGGER = Loggers.get(DisplayIssuesInScanner.class);

  @Override
  public void describe(PostJobDescriptor descriptor) {
    descriptor.name("Display issues");
  }

  @Override
  public void execute(PostJobContext context) {
    // issues are not accessible when the mode "issues" is not enabled
    // with the scanner property "sonar.analysis.mode=issues"
    if (context.analysisMode().isIssues()) {
      // all open issues
      for (PostJobIssue issue : context.issues()) {
        String ruleKey = issue.ruleKey().toString();
        Integer issueLine = issue.line();

        // just to illustrate, we dump some fields of the 'issue' in sysout (bad, very bad)
        LOGGER.info("OPEN {} : {}({})", ruleKey, issue.componentKey(), issueLine);
      }

      // all resolved issues
      for (PostJobIssue issue : context.resolvedIssues()) {
        LOGGER.info("RESOLVED {} : {}({})", issue.ruleKey(), issue.componentKey(), issue.line());
      }
    }
  }
}
