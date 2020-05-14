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

  @Override
  public String getDescription() {
    return "Display Quality Gate status";
  }
}
