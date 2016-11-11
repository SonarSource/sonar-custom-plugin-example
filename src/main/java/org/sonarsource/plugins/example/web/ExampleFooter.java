package org.sonarsource.plugins.example.web;

import org.sonar.api.web.Footer;

public final class ExampleFooter implements Footer {

  @Override
  public String getHtml() {
    return "<p>Footer Example - <em>This is static HTML</em></p>";
  }
}
