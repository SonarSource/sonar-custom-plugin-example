/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
import React from 'react';

export default class QualityGate extends React.PureComponent {

  render() {
      if ( this.props.qg_status === 'OK' ) {
        return ( <span className="level level-OK">Passed</span> );
      } else if ( this.props.qg_status === 'WARN' ) {
        return ( <span className="level level-WARN">Warning</span> );
      } else {
        return ( <span className="level level-KO custom-abc">Failed</span> );
      }
  }
}
