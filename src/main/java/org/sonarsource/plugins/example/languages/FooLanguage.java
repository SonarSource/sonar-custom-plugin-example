package org.sonarsource.plugins.example.languages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

/**
 * This class defines the fictive Foo language.
 */
public final class FooLanguage extends AbstractLanguage {

  public static final String NAME = "Foo";
  public static final String KEY = "foo";
  public static final String FILE_SUFFIXES_PROPERTY_KEY = "sonar.foo.file.suffixes";
  public static final String DEFAULT_FILE_SUFFIXES = "foo";

  private final Settings settings;

  public FooLanguage(Settings settings) {
    super(KEY, NAME);
    this.settings = settings;
  }

  @Override
  public String[] getFileSuffixes() {
    String[] suffixes = filterEmptyStrings(settings.getStringArray(FILE_SUFFIXES_PROPERTY_KEY));
    if (suffixes.length == 0) {
      suffixes = StringUtils.split(DEFAULT_FILE_SUFFIXES, ",");
    }
    return suffixes;
  }

  private String[] filterEmptyStrings(String[] stringArray) {
    List<String> nonEmptyStrings = new ArrayList<>();
    for (String string : stringArray) {
      if (StringUtils.isNotBlank(string.trim())) {
        nonEmptyStrings.add(string.trim());
      }
    }
    return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
  }

}
