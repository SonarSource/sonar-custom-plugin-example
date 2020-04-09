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
package org.sonarsource.plugins.example.measures;

import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import static org.sonarsource.plugins.example.measures.ExampleMetrics.FILENAME_SIZE;
import static org.sonarsource.plugins.example.measures.ExampleMetrics.FILENAME_SIZE_RATING;

/**
 * Rating is computed from value of metric {@link ExampleMetrics#FILENAME_SIZE}.
 */
public class ComputeSizeRating implements MeasureComputer {

  private static final int THRESHOLD = 20;
  private static final int RATING_A = 1;
  private static final int RATING_B = 2;

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return def.newDefinitionBuilder()
      .setInputMetrics(FILENAME_SIZE.key())
      .setOutputMetrics(FILENAME_SIZE_RATING.key())
      .build();
  }

  @Override
  public void compute(MeasureComputerContext context) {
    Measure size = context.getMeasure(FILENAME_SIZE.key());
    if (size != null) {
      // rating values are currently implemented as integers in API
      int rating = RATING_A;
      if (size.getIntValue() > THRESHOLD) {
        rating = RATING_B;
      }
      context.addMeasure(FILENAME_SIZE_RATING.key(), rating);
    }
  }
}
