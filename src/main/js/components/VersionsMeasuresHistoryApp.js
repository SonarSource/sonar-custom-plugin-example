/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
import React from 'react';
import MeasuresHistory from './MeasuresHistory'
import {translate} from '../common/l10n.js'
import {findVersionsAndMeasures} from '../api.js'
import {stringifyQPDetails} from '../api.js'

export default class VersionsMeasuresHistoryApp extends React.PureComponent {

  state = {
    data: []
  };

  componentDidMount() {
    findVersionsAndMeasures(this.props.project).then(
      (valuesReturnedByAPI) => {
        this.setState({
          data: valuesReturnedByAPI
        });
      }
    );
  }

  render() {
    // Data Gathered: {JSON.stringify(this.state.data)}
    return (
      <div className="page page-limited">
        <table className="data zebra">
          <thead><tr className="code-components-header">
            <th className="thin nowrap text-left code-components-cell">Version</th>

            <th className="thin nowrap text-center code-components-cell">Quality Gate</th>

            <th className="thin nowrap text-right code-components-cell">{translate('issue.type.BUG.plural')}</th>
            <th className="thin nowrap text-right code-components-cell">Reliability Rating</th>

            <th className="thin nowrap text-right code-components-cell">Vulnerabilities</th>
            <th className="thin nowrap text-right code-components-cell">Security Rating</th>

            <th className="thin nowrap text-right code-components-cell">Code Smells</th>
            <th className="thin nowrap text-right code-components-cell">Maintainability Rating</th>
          </tr></thead>
          <tbody>
          {this.state.data.map(
              (value,idx) =>
              <MeasuresHistory
                measure={value}
                key={idx}
              />
              )
          }
          </tbody>
        </table>
      </div>
    );
  }
}
