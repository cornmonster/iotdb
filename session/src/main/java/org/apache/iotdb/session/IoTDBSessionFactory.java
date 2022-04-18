package org.apache.iotdb.session;

import java.util.function.Supplier;

public interface IoTDBSessionFactory extends Supplier<IoTDBSession> {
  @Override
  IoTDBSession get();
}
