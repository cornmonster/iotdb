package org.apache.iotdb.session;

public interface IoTDBSessionPool {

  IoTDBSession acquire();

  void release(IoTDBSession session);
}
