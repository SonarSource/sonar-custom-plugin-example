/*
 * Copyright (C) 2009-2020 SonarSource SA
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

export default function MeasuresHistory(props) {
  return (
    <tr>
      <td className="thin nowrap text-center">
        <div className="code-components-cell">
          <span>
            {props.measure.alert_status || "OK"}
          </span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell"><span>{props.measure.bugs}</span></div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>
            {formatRating(props.measure.reliability_rating || 1)}
          </span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>{props.measure.vulnerabilities}</span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>
          {formatRating(props.measure.security_rating || 1)}
          </span>
        </div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell"><span>{props.measure.sqale_index}</span></div>
      </td>
      <td className="thin nowrap text-right">
        <div className="code-components-cell">
          <span>
          {formatRating(props.measure.sqale_rating || 1)}
          </span>
        </div>
      </td>
    </tr>
  );
}

function formatRating(rating: number) {
  return String.fromCharCode('A'.charCodeAt(0) - 1 + +rating);
}
