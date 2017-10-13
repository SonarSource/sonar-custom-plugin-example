/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
import React from 'react';
import { render, unmountComponentAtNode } from 'react-dom';
import VersionsMeasuresHistoryApp from './components/VersionsMeasuresHistoryApp';
import './style.css';

window.registerExtension('example/measures_history', options => {

  const { el } = options;

  render(
          <VersionsMeasuresHistoryApp
            project={options.component}
          />, el
  );

  return () => unmountComponentAtNode(el);
});
