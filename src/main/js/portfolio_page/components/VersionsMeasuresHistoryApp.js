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
import { findVersionsAndMeasures } from "../../common/api";
import MeasuresHistory from "./MeasuresHistory";

export default class VersionsMeasuresHistoryApp extends React.PureComponent {
  state = {
    loading: true,
    data: []
  };

  componentDidMount() {
    findVersionsAndMeasures(this.props.project).then(data => {
      this.setState({
        loading: false,
        data
      });
    });
  }

  render() {
    if (this.state.loading) {
      return (
        <div className="page page-limited">
          Loading...
        </div>
      );
    }

    return (
      <div className="page page-limited">
        <table className="data zebra">
          <thead>
            <tr className="code-components-header">
              <th className="thin nowrap text-center code-components-cell">
                {window.t("example.portfolio_page.qg")}
              </th>
              <th className="thin nowrap text-right code-components-cell">
                {window.t("example.portfolio_page.bugs")}
              </th>
              <th className="thin nowrap text-right code-components-cell">
                {window.t("example.portfolio_page.reliability_rating")}
              </th>

              <th className="thin nowrap text-right code-components-cell">
                {window.t("example.portfolio_page.vulnerabilities")}
              </th>
              <th className="thin nowrap text-right code-components-cell">
                {window.t("example.portfolio_page.security_rating")}
              </th>

              <th className="thin nowrap text-right code-components-cell">
                {window.t("example.portfolio_page.code_smells_effort")}
              </th>
              <th className="thin nowrap text-right code-components-cell">
                {window.t("example.portfolio_page.maintainability_rating")}
              </th>
            </tr>
          </thead>
          <tbody>
            {this.state.data !== undefined &&
              this.state.data.map((value, idx) => <MeasuresHistory measure={value} key={idx} />)}
          </tbody>
        </table>
      </div>
    );
  }
}
