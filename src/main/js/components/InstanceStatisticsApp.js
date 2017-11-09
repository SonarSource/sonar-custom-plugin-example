/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
import React from 'react';
import {translate} from '../common/l10n.js'
import {findQualityProfilesStatistics} from '../api.js'
import {findQualityQatesStatistics} from '../api.js'
import {findIssuesStatistics} from '../api.js'
import {findProjects} from '../api.js'

export default class InstanceStatisticsApp extends React.PureComponent {

  state = {
    numberOfQualityProfiles: '',
    numberOfQualityQates: '',
    numberOfIssues: '',
    numberOfProjects: ''
  };

  componentDidMount() {
    findQualityProfilesStatistics().then(
        (valuesReturnedByAPI) => {
            this.setState({
                numberOfQualityProfiles: valuesReturnedByAPI
            });
        }
    );
    findQualityQatesStatistics().then(
        (valuesReturnedByAPI) => {
            this.setState({
                numberOfQualityQates: valuesReturnedByAPI
            });
        }
    );
    findIssuesStatistics().then(
        (valuesReturnedByAPI) => {
            this.setState({
                numberOfIssues: valuesReturnedByAPI
            });
        }
    );
    findProjects().then(
        (valuesReturnedByAPI) => {
            this.setState({
                numberOfProjects: valuesReturnedByAPI
            });
        }
    );
  }

  render() {
    return (
      <div className="page page-limited sanity-check">
        <table className="data zebra">
          <tbody>
          <tr>
            <td className="code-name-cell"># Quality Profiles</td>
            <td className="thin nowrap text-right">{this.state.numberOfQualityProfiles}</td>
          </tr>
          <tr>
            <td className="code-name-cell"># Quality Gates</td>
            <td className="thin nowrap text-right">{this.state.numberOfQualityQates}</td>
          </tr>
          <tr>
            <td className="code-name-cell"># Issues</td>
            <td className="thin nowrap text-right">{this.state.numberOfIssues}</td>
          </tr>
          <tr>
            <td className="code-name-cell"># Projects</td>
            <td className="thin nowrap text-right">{this.state.numberOfProjects}</td>
          </tr>
          </tbody>
        </table>
      </div>
    );
  }
}
