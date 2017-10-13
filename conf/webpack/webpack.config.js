/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
const path = require('path');
const autoprefixer = require('autoprefixer');

const autoprefixerOptions = {
  browsers: [
    'last 3 Chrome versions',
    'last 3 Firefox versions',
    'Safari >= 8',
    'Edge >= 12',
    'IE 11'
  ]
};

const output = path.join(__dirname, '../../target/classes/static');

module.exports = {
  entry: {
    'measures_history': ['./src/main/js/app-measures_history.js'],
    'sanity_check': ['./src/main/js/app-sanity_check.js'],
  },
  output: {
    path: output,
    filename: '[name].js'
  },
  resolve: {
    root: path.join(__dirname, 'src/main/js')
  },
  externals: {
    lodash: '_',
    react: 'React',
    'react-dom': 'ReactDOM',
    'react-redux': 'ReactRedux',
    'react-router': 'ReactRouter',
    'sonar-request': 'SonarRequest',
    'sonar-measures': 'SonarMeasures',
    'sonar-components': 'SonarComponents'
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        loader: 'babel',
        exclude: /(node_modules)/
      },
      {
        test: /\.css/,
        loader: 'style-loader!css-loader!postcss-loader'
      },
      { test: /\.json$/, loader: 'json' }
    ]
  },
  postcss() {
    return [autoprefixer(autoprefixerOptions)];
  }
};
