package org.apache.iotdb.session;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.template.Template;
import org.apache.iotdb.session.util.Version;
import org.apache.iotdb.tsfile.file.metadata.enums.CompressionType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSEncoding;
import org.apache.iotdb.tsfile.write.record.Tablet;

import org.apache.thrift.TException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IoTDBSession {

  Version getVersion();

  int getFetchSize();

  long getQueryTimeout();

  String getTimeZone();

  // TODO: doesn't make sense in standalone mode.
  boolean isEnableQueryRedirection();

  // TODO: doesn't make sense in standalone mode.
  boolean isEnableCacheLeader();

  void open() throws IoTDBConnectionException;

  // TODO: TException shouldn't be here.
  // TODO: replace with getServerProperties?
  String getTimestampPrecision() throws TException;

  void setStorageGroup(String storageGroup)
      throws IoTDBConnectionException, StatementExecutionException;

  void deleteStorageGroup(String storageGroup)
      throws IoTDBConnectionException, StatementExecutionException;

  void deleteStorageGroups(List<String> storageGroups)
      throws IoTDBConnectionException, StatementExecutionException;

  void createTimeseries(TimeseriesBuilder builder)
      throws IoTDBConnectionException, StatementExecutionException;

  // TODO: necessary?
  void createAlignedTimeseries(AlignedTimeseriesBuilder builder);

  void createTimeseries(List<TimeseriesBuilder> builders);

  boolean checkTimeseriesExists(String path);

  SessionDataSet executeQueryStatement(String sql);

  SessionDataSet executeQueryStatement(String sql, long timeoutInMs);

  // TODO: doesn't make sense in standalone mode.
  SessionDataSet executeStatementMayRedirect(String sql, long timeoutInMs);

  void executeNonQueryStatement(String sql);

  // TODO:
  SessionDataSet executeRawDataQuery(List<String> paths, long startTime, long endTime);

  // TODO:
  SessionDataSet executeLastDataQuery(List<String> paths, long LastTime);

  // TODO:
  SessionDataSet executeLastDataQuery(List<String> paths);

  // TODO: not a good practice.
  void insertRecord(
      String deviceId,
      long time,
      List<String> measurements,
      List<TSDataType> types,
      Object... values)
      throws IoTDBConnectionException, StatementExecutionException;

  // TODO: use a builder.
  void insertRecord(
      String deviceId,
      long time,
      List<String> measurements,
      List<TSDataType> types,
      List<Object> values)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertRecord(String deviceId, long time, List<String> measurements, List<String> values)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertAlignedRecord(
      String deviceId,
      long time,
      List<String> measurements,
      List<TSDataType> types,
      List<Object> values);

  void insertAlignedRecord(
      String deviceId, long time, List<String> measurements, List<String> values)
      throws IoTDBConnectionException, StatementExecutionException;

  // TODO: use a builder.
  public void insertRecords(
      List<String> deviceIds,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<String>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertRecords(
      List<String> deviceIds,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<TSDataType>> typesList,
      List<List<Object>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  // TODO: use a builder.
  void insertAlignedRecords(
      List<String> deviceIds,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<String>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertAlignedRecords(
      List<String> deviceIds,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<TSDataType>> typesList,
      List<List<Object>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  // TODO: not a good practice.
  void insertRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<TSDataType>> typesList,
      List<List<Object>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  // TODO: not a good practice.
  void insertRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<TSDataType>> typesList,
      List<List<Object>> valuesList,
      boolean haveSorted)
      throws IoTDBConnectionException, StatementExecutionException;

  // TODO: not a good practice.
  void insertStringRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<String>> valuesList,
      boolean haveSorted)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertStringRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<String>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertAlignedRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<TSDataType>> typesList,
      List<List<Object>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertAlignedRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<TSDataType>> typesList,
      List<List<Object>> valuesList,
      boolean haveSorted)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertAlignedStringRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<String>> valuesList,
      boolean haveSorted)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertAlignedStringRecordsOfOneDevice(
      String deviceId,
      List<Long> times,
      List<List<String>> measurementsList,
      List<List<String>> valuesList)
      throws IoTDBConnectionException, StatementExecutionException;

  void insertTablet(Tablet tablet);

  void insertTablet(Tablet tablet, boolean sorted);

  void insertAlignedTablet(Tablet tablet);

  void insertAlignedTablet(Tablet tablet, boolean sorted);

  void insertTablets(Map<String, Tablet> tablets);

  void insertTablets(Map<String, Tablet> tablets, boolean sorted);

  void insertAlignedTablets(Map<String, Tablet> tablets);

  void insertAlignedTablets(Map<String, Tablet> tablets, boolean sorted);

  void setSchemaTemplate(String templateName, String prefixPath);

  void createSchemaTemplate(Template template);

  void createSchemaTemplate(
      String templateName,
      List<String> measurements,
      List<TSDataType> dataTypes,
      List<TSEncoding> encodings,
      List<CompressionType> compressors,
      boolean isAligned)
      throws IOException, IoTDBConnectionException, StatementExecutionException;

  void addAlignedMeasurementsInTemplate(
      String templateName,
      List<String> measurementsPath,
      List<TSDataType> dataTypes,
      List<TSEncoding> encodings,
      List<CompressionType> compressors)
      throws IOException, IoTDBConnectionException, StatementExecutionException;

  void addAlignedMeasurementInTemplate(
      String templateName,
      String measurementPath,
      TSDataType dataType,
      TSEncoding encoding,
      CompressionType compressor)
      throws IOException, IoTDBConnectionException, StatementExecutionException;

  void addUnalignedMeasurementsInTemplate(
      String templateName,
      List<String> measurementsPath,
      List<TSDataType> dataTypes,
      List<TSEncoding> encodings,
      List<CompressionType> compressors)
      throws IOException, IoTDBConnectionException, StatementExecutionException;

  void addUnalignedMeasurementInTemplate(
      String templateName,
      String measurementPath,
      TSDataType dataType,
      TSEncoding encoding,
      CompressionType compressor)
      throws IOException, IoTDBConnectionException, StatementExecutionException;

  void deleteNodeInTemplate(String templateName, String path);

  int countMeasurementsInTemplate(String name);

  boolean isMeasurementInTemplate(String templateName, String path);

  boolean isPathExistInTemplate(String templateName, String path);

  List<String> showMeasurementsInTemplate(String templateName);

  List<String> showMeasurementsInTemplate(String templateName, String pattern);

  // TODO: showTemplates?
  List<String> showAllTemplates();

  List<String> showPathsTemplateSetOn(String templateName);

  List<String> showPathsTemplateUsingOn(String templateName);

  void unsetSchemaTemplate(String prefixPath, String templateName);

  void dropSchemaTemplate(String templateName);

  void close() throws IoTDBConnectionException;
}
