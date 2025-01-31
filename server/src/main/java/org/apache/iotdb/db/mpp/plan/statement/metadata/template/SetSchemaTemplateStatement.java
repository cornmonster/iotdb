/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iotdb.db.mpp.plan.statement.metadata.template;

import org.apache.iotdb.commons.exception.IllegalPathException;
import org.apache.iotdb.commons.path.PartialPath;
import org.apache.iotdb.db.mpp.plan.analyze.QueryType;
import org.apache.iotdb.db.mpp.plan.constant.StatementType;
import org.apache.iotdb.db.mpp.plan.statement.IConfigStatement;
import org.apache.iotdb.db.mpp.plan.statement.StatementVisitor;
import org.apache.iotdb.db.mpp.plan.statement.metadata.ShowStatement;

public class SetSchemaTemplateStatement extends ShowStatement implements IConfigStatement {

  private String templateName;
  private PartialPath path;

  public SetSchemaTemplateStatement(String templateName, PartialPath path) {
    super();
    statementType = StatementType.SET_SCHEMA_TEMPLATE;
    this.templateName = templateName;
    this.path = path;
  }

  public SetSchemaTemplateStatement(String templateName, String path) throws IllegalPathException {
    super();
    statementType = StatementType.SET_SCHEMA_TEMPLATE;
    this.templateName = templateName;
    this.path = new PartialPath(path);
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public PartialPath getPath() {
    return path;
  }

  public void setPath(PartialPath path) {
    this.path = path;
  }

  @Override
  public <R, C> R accept(StatementVisitor<R, C> visitor, C context) {
    return visitor.visitSetSchemaTemplate(this, context);
  }

  @Override
  public QueryType getQueryType() {
    return QueryType.WRITE;
  }
}
