/*
 * Copyright (C) 2017-2017 SonarSource SA
 * All rights reserved
 * mailto:info AT sonarsource DOT com
 */
export function translate() {
  return window.t.apply(this, arguments);
}

export function translateWithParameters() {
  return window.tp.apply(this, arguments);
}
