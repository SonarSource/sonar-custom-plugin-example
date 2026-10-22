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
process.env.NODE_ENV = 'development';
require('dotenv').config({ quiet: true });

const webpack = require('webpack');
const WebpackDevServer = require('webpack-dev-server');
const config = require('../conf/webpack/webpack.config.dev');

async function start() {
  const server = new WebpackDevServer({
    host: process.env.HOST || 'localhost',
    port: process.env.PORT ? Number(process.env.PORT) : 3000,
    server: process.env.HTTPS === 'true' ? 'https' : 'http',
    compress: true,
    static: false,
    hot: true,
    devMiddleware: { publicPath: config.output.publicPath },
    proxy: [{
      context: pathname => !pathname.startsWith(config.output.publicPath) && pathname !== '/ws',
      target: process.env.PROXY_URL || 'http://localhost:9000',
      changeOrigin: true,
      secure: false
    }]
  }, webpack(config));

  await server.start();
}

start().catch(error => {
  console.error(error);
  process.exitCode = 1;
});
