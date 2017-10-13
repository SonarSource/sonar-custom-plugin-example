/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
import React from 'react';
import { render, unmountComponentAtNode } from 'react-dom';
import InstanceStatisticsApp from './components/InstanceStatisticsApp';
import './style.css';

window.registerExtension('example/sanity_check', options => {

  const { el } = options;

  render(
          <InstanceStatisticsApp/>, el
  );

  return () => unmountComponentAtNode(el);
});
