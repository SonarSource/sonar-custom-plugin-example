/*
 * Copyright (C) SonarSource Sàrl
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
const webpack = require('webpack');
const config = require('./webpack.config');
const getClientEnvironment = require('../env');

// Get environment variables to inject into our app.
const env = getClientEnvironment();

// Assert this just to be safe.
// Development builds of React are slow and not intended for production.
if (env['process.env.NODE_ENV'] !== '"production"') {
  throw new Error('Production builds must have NODE_ENV=production.');
}

config.mode = 'production';
config.bail = true;
config.plugins = [new webpack.DefinePlugin(env)];
config.optimization = {
  minimize: !process.argv.includes('--no-uglify')
};

module.exports = config;
