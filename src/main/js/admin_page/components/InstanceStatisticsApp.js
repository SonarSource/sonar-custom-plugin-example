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
import {
  findIssuesStatistics,
  findProjects,
  findQualityProfilesStatistics,
  findQualityQatesStatistics
} from "../../common/api";

export default class InstanceStatisticsApp extends React.PureComponent {
  state = {
    loading: true,
    numberOfQualityProfiles: "",
    numberOfQualityGates: "",
    numberOfIssues: "",
    numberOfProjects: ""
  };

  componentDidMount() {
    Promise.all([
      findQualityProfilesStatistics(),
      findQualityQatesStatistics(),
      findIssuesStatistics(),
      findProjects()
    ]).then(([numberOfQualityProfiles, numberOfQualityGates, numberOfIssues, numberOfProjects]) => {
      this.setState({
        loading: false,
        numberOfQualityProfiles,
        numberOfQualityGates,
        numberOfIssues,
        numberOfProjects
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
      <div className="page page-limited sanity-check">
        <table className="data zebra">
          <tbody>
            <tr>
              <td className="code-name-cell">
                {window.tp(
                  "example.admin_page.we_have_x_y",
                  this.state.numberOfQualityProfiles,
                  window.t("example.admin_page.quality_profiles")
                )}
              </td>
            </tr>
            <tr>
              <td className="code-name-cell">
                {window.tp(
                  "example.admin_page.we_have_x_y",
                  this.state.numberOfQualityGates,
                  window.t("example.admin_page.quality_gates")
                )}
              </td>
            </tr>
            <tr>
              <td className="code-name-cell">
                {window.tp(
                  "example.admin_page.we_have_x_y",
                  this.state.numberOfIssues,
                  window.t("example.admin_page.issues")
                )}
              </td>
            </tr>
            <tr>
              <td className="code-name-cell">
                {window.tp(
                  "example.admin_page.we_have_x_y",
                  this.state.numberOfProjects,
                  window.t("example.admin_page.projects")
                )}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}
