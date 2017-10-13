/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
window.baseUrl = '';

window.t = (window.tp = function() {
  const args = Array.prototype.slice.call(arguments, 0);
  return args.join('.');
});
