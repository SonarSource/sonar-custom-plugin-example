/*
 * Copyright (C) 2009-2019 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
import React from "react";
// SonarComponents (referenced as sonar-components here, see the Webpack config)
// exposes React components exposed by SonarQube.
import { Level, Rating } from "sonar-components";
// SonarMeasures (referenced as sonar-measures here, see the Webpack config)
// exposes helper functions for formatting and handling measure values.
import { formatMeasure } from "sonar-measures";

export default function MeasuresHistory(props) {
  return (
    <tr>
      <td className="thin nowrap text-center">
        <div className="code-components-cell">
          <span>
            <Level level={props.measure.alert_status || "OK"} />
          </span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell"><span>{formatMeasure(props.measure.bugs, "SHORT_INT")}</span></div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>
            <Rating value={props.measure.reliability_rating || 1} />
          </span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>{formatMeasure(props.measure.vulnerabilities, "SHORT_INT")}</span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>
            <Rating value={props.measure.security_rating || 1} />
          </span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell"><span>{formatMeasure(props.measure.sqale_index, "SHORT_WORK_DUR")}</span></div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>
            <Rating value={props.measure.sqale_rating || 1} />
          </span>
        </div>
      </td>
    </tr>
  );
}
