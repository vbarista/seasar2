/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.jdbc.DbmsDialect;
import org.seasar.extension.jdbc.PropertyType;
import org.seasar.extension.jdbc.ResultSetHandler;

/**
 * Beanのリストを返す {@link ResultSetHandler}です。
 * 
 * @author higa
 * 
 */
public class BeanListResultSetHandler extends AbstractBeanResultSetHandler {

    /**
     * {@link BeanListResultSetHandler}を作成します。
     * 
     * @param beanClass
     *            Beanクラス
     * @param dialect
     *            データベースの方言
     * @param sql
     *            SQL
     */
    public BeanListResultSetHandler(Class<?> beanClass, DbmsDialect dialect,
            String sql) {
        super(beanClass, dialect, sql);
    }

    public Object handle(ResultSet rs) throws SQLException {
        PropertyType[] propertyTypes = createPropertyTypes(rs.getMetaData());
        List<Object> list = new ArrayList<Object>(100);
        while (rs.next()) {
            Object row = createRow(rs, propertyTypes);
            list.add(row);
        }
        return list;
    }
}