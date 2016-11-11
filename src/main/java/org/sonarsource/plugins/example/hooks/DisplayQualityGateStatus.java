package org.sonarsource.plugins.example.hooks;

import org.sonar.api.ce.posttask.PostProjectAnalysisTask;
import org.sonar.api.ce.posttask.QualityGate;
import org.sonar.api.utils.log.Loggers;

/**
 * Logs the Quality gate status in Compute Engine when analysis is finished (browse
 * Administration > Projects > Background Tasks).
 * A real use-case would be to send an email or to notify an IRC channel.
 */
public class DisplayQualityGateStatus implements PostProjectAnalysisTask {
  @Override
  public void finished(ProjectAnalysis analysis) {
    QualityGate gate = analysis.getQualityGate();
    if (gate != null) {
      Loggers.get(getClass()).info("Quality gate is " + gate.getStatus());
    }
  }
}
