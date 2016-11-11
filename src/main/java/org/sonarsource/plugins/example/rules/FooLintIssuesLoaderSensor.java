package org.sonarsource.plugins.example.rules;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.config.Settings;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.plugins.example.languages.FooLanguage;

/**
 * The goal of this Sensor is to load the results of an analysis performed by a fictive external tool named: FooLint
 * Results are provided as an xml file and are corresponding to the rules defined in 'rules.xml'.
 * To be very abstract, these rules are applied on source files made with the fictive language Foo.
 */
public class FooLintIssuesLoaderSensor implements Sensor {

  private static final Logger LOGGER = Loggers.get(FooLintIssuesLoaderSensor.class);

  protected static final String REPORT_PATH_KEY = "sonar.foolint.reportPath";

  protected final Settings settings;
  protected final FileSystem fileSystem;
  protected SensorContext context;

  /**
   * Use of IoC to get Settings, FileSystem, RuleFinder and ResourcePerspectives
   */
  public FooLintIssuesLoaderSensor(final Settings settings, final FileSystem fileSystem) {
    this.settings = settings;
    this.fileSystem = fileSystem;
  }

  @Override
  public void describe(final SensorDescriptor descriptor) {
    descriptor.name("FooLint Issues Loader Sensor");
    descriptor.onlyOnLanguage(FooLanguage.KEY);
  }

  protected String reportPathKey() {
    return REPORT_PATH_KEY;
  }

  protected String getReportPath() {
    String reportPath = settings.getString(reportPathKey());
    if (!StringUtils.isEmpty(reportPath)) {
      return reportPath;
    } else {
      return null;
    }
  }

  @Override
  public void execute(final SensorContext context) {
    if (!StringUtils.isEmpty(getReportPath())) {
      this.context = context;
      String reportPath = getReportPath();
      File analysisResultsFile = new File(reportPath);
      try {
        parseAndSaveResults(analysisResultsFile);
      } catch (XMLStreamException e) {
        throw new IllegalStateException("Unable to parse the provided FooLint file", e);
      }
    }
  }

  protected void parseAndSaveResults(final File file) throws XMLStreamException {
    LOGGER.info("(mock) Parsing 'FooLint' Analysis Results");
    FooLintAnalysisResultsParser parser = new FooLintAnalysisResultsParser();
    List<FooLintError> errors = parser.parse(file);
    for (FooLintError error : errors) {
      getResourceAndSaveIssue(error);
    }
  }

  private void getResourceAndSaveIssue(final FooLintError error) {
    LOGGER.debug(error.toString());

    InputFile inputFile = fileSystem.inputFile(
      fileSystem.predicates().and(
        fileSystem.predicates().hasRelativePath(error.getFilePath()),
        fileSystem.predicates().hasType(InputFile.Type.MAIN)));

    LOGGER.debug("inputFile null ? " + (inputFile == null));

    if (inputFile != null) {
      saveIssue(inputFile, error.getLine(), error.getType(), error.getDescription());
    } else {
      LOGGER.error("Not able to find a InputFile with " + error.getFilePath());
    }
  }

  private void saveIssue(final InputFile inputFile, int line, final String externalRuleKey, final String message) {
    RuleKey ruleKey = RuleKey.of(getRepositoryKeyForLanguage(inputFile.language()), externalRuleKey);

    NewIssue newIssue = context.newIssue()
      .forRule(ruleKey);

    NewIssueLocation primaryLocation = newIssue.newLocation()
      .on(inputFile)
      .message(message);
    if (line > 0) {
      primaryLocation.at(inputFile.selectLine(line));
    }
    newIssue.at(primaryLocation);

    newIssue.save();
  }

  private static String getRepositoryKeyForLanguage(String languageKey) {
    return languageKey.toLowerCase() + "-" + FooLintRulesDefinition.KEY;
  }

  @Override
  public String toString() {
    return "FooLintIssuesLoaderSensor";
  }

  private class FooLintError {

    private final String type;
    private final String description;
    private final String filePath;
    private final int line;

    public FooLintError(final String type, final String description, final String filePath, final int line) {
      this.type = type;
      this.description = description;
      this.filePath = filePath;
      this.line = line;
    }

    public String getType() {
      return type;
    }

    public String getDescription() {
      return description;
    }

    public String getFilePath() {
      return filePath;
    }

    public int getLine() {
      return line;
    }

    @Override
    public String toString() {
      StringBuilder s = new StringBuilder();
      s.append(type);
      s.append("|");
      s.append(description);
      s.append("|");
      s.append(filePath);
      s.append("(");
      s.append(line);
      s.append(")");
      return s.toString();
    }
  }

  private class FooLintAnalysisResultsParser {

    public List<FooLintError> parse(final File file) throws XMLStreamException {
      LOGGER.info("Parsing file {}", file.getAbsolutePath());

      // as the goal of this example is not to demonstrate how to parse an xml file we return an hard coded list of FooError

      FooLintError fooError1 = new FooLintError("ExampleRule1", "More precise description of the error", "src/MyClass.foo", 5);
      FooLintError fooError2 = new FooLintError("ExampleRule2", "More precise description of the error", "src/MyClass.foo", 9);

      return Arrays.asList(fooError1, fooError2);
    }
  }

}
