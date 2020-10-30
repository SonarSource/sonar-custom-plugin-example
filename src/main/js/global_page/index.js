/*
 * Copyright (C) 2009-2020 SonarSource SA
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
import * as app from "./app";
import "./app.css";

// This creates a page for portfolios, which generates a report for all the
// projects inside the portfolio.
//
//  You can access it at /extension/example/global_page
window.registerExtension('example/global_page', function (options) {
  // options.el contains the DOM node we can use for our app. Call the start
  // method to initialize the application, and pass it this DOM node.
  app.start(options.el);

  // Return the shutdown function.
  return function () {
    // When the user leaves our page, we have the opportunity to cleanly
    // shutdown out application. Do whatever is necessary (removing state, event
    // listeners, etc) in this function.
    app.stop();
  };
});
