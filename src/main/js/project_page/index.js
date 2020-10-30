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
// Necessary for setting up, because of Webpack.
import Backbone from "backbone";
import $ from 'jquery';
import _ from "underscore";
window.$ = $;
window._ = _;
window.Backbone = Backbone;
window.app = window.app || {};

// Load the actual app logic.
require("./view/AppView");

// This creates a page for any component (project, portfolio, etc).
//
//  You can access it at /project/extension/example/project_page?id={COMPONENT_ID}
window.registerExtension('example/project_page', function (options) {
  // options.el contains the DOM node we can use for our app. Prepare our node
  // so our Backbone View can correctly target it.
  options.el.innerHTML = `<div class="page page-limited" id="example-project_page">Loading...</div>`;

  // Start the view.
  var view = new app.AppView({ branchLike: options.branchLike });
  view.render();

  // Return the shutdown function.
  return function () {
    // When the user leaves our page, we have the opportunity to cleanly
    // shutdown out application. Let's clean up the view by removing it 
    // completely.
    view.remove();
  };
});
