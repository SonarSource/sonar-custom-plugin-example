/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
import React from 'react';
import QualityGate from './QualityGate'
import Rating from './Rating'

export default class MeasuresHistory extends React.PureComponent {

  render() {
    return (
      <tr>
        <td className="code-name-cell">{this.props.measure.version}</td>

        <td className="thin nowrap text-center"><div className="code-components-cell"><span>
         <QualityGate
          qg_status={this.props.measure.alert_status}
          /></span></div></td>
        <td className="thin nowrap text-right"><div className="code-components-cell"><span>{this.props.measure.bugs}</span></div></td>
        <td className="thin nowrap text-right"><div className="code-components-cell"><span>
         <Rating
          rating={this.props.measure.reliability_rating}
          /></span></div></td>
        <td className="thin nowrap text-right"><div className="code-components-cell"><span>{this.props.measure.vulnerabilities}</span></div></td>
        <td className="thin nowrap text-right"><div className="code-components-cell"><span>
         <Rating
          rating={this.props.measure.security_rating}
          /></span></div></td>
        <td className="thin nowrap text-right"><div className="code-components-cell"><span>{this.props.measure.code_smells}</span></div></td>
        <td className="thin nowrap text-right"><div className="code-components-cell"><span>
         <Rating
          rating={this.props.measure.sqale_rating}
          /></span></div></td>
      </tr>
    );
  }
}
