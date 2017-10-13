/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
import React from 'react';

export default class Rating extends React.PureComponent {

  render() {
    if ( this.props.rating === '1.0') {
      return ( <span className="rating rating-A">A</span> );
    } else if ( this.props.rating === '2.0') {
      return ( <span className="rating rating-B">B</span> );
    } else if ( this.props.rating === '3.0') {
      return ( <span className="rating rating-C">C</span> );
    } else if ( this.props.rating === '4.0') {
      return ( <span className="rating rating-D">D</span> );
    } else {
      return ( <span className="rating rating-E">E</span> );
    }
  }
}
