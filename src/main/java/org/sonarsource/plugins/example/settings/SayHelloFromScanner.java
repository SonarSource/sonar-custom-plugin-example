package org.sonarsource.plugins.example.settings;

import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Loggers;

public class SayHelloFromScanner implements Sensor {

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name(getClass().getName());
  }

  @Override
  public void execute(SensorContext context) {
    if (context.settings().getBoolean(HelloWorldProperties.HELLO_KEY)) {
      // print log only if property is set to true
      Loggers.get(getClass()).info("Hello World!");
    }
  }

}
