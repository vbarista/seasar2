/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.extension.jdbc.gen.desc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;
import org.seasar.extension.jdbc.EntityMeta;
import org.seasar.extension.jdbc.gen.PrimaryKeyDesc;
import org.seasar.extension.jdbc.gen.desc.PrimaryKeyDescFactoryImpl;
import org.seasar.extension.jdbc.gen.dialect.HsqlGenDialect;
import org.seasar.extension.jdbc.meta.ColumnMetaFactoryImpl;
import org.seasar.extension.jdbc.meta.EntityMetaFactoryImpl;
import org.seasar.extension.jdbc.meta.PropertyMetaFactoryImpl;
import org.seasar.extension.jdbc.meta.TableMetaFactoryImpl;
import org.seasar.framework.convention.PersistenceConvention;
import org.seasar.framework.convention.impl.PersistenceConventionImpl;

import static org.junit.Assert.*;

/**
 * @author taedium
 * 
 */
public class PrimaryKeyDescFactoryImplTest {

    private EntityMetaFactoryImpl entityMetaFactory;

    private PrimaryKeyDescFactoryImpl primaryKeyDescFactory;

    @Before
    public void setUp() throws Exception {
        PersistenceConvention pc = new PersistenceConventionImpl();
        ColumnMetaFactoryImpl cmf = new ColumnMetaFactoryImpl();
        cmf.setPersistenceConvention(pc);
        PropertyMetaFactoryImpl pmf = new PropertyMetaFactoryImpl();
        pmf.setPersistenceConvention(pc);
        pmf.setColumnMetaFactory(cmf);
        TableMetaFactoryImpl tmf = new TableMetaFactoryImpl();
        tmf.setPersistenceConvention(pc);
        entityMetaFactory = new EntityMetaFactoryImpl();
        entityMetaFactory.setPersistenceConvention(pc);
        entityMetaFactory.setPropertyMetaFactory(pmf);
        entityMetaFactory.setTableMetaFactory(tmf);
        primaryKeyDescFactory = new PrimaryKeyDescFactoryImpl(
                new HsqlGenDialect());
    }

    @Test
    public void testId_assigned() throws Exception {
        EntityMeta entityMeta = entityMetaFactory.getEntityMeta(Aaa.class);
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta);
        assertNotNull(primaryKeyDesc);
        assertEquals(1, primaryKeyDesc.getColumnNameList().size());
        assertEquals("ID", primaryKeyDesc.getColumnNameList().get(0));
        assertFalse(primaryKeyDesc.isIdentity());
    }

    @Test
    public void testId_identity() throws Exception {
        EntityMeta entityMeta = entityMetaFactory.getEntityMeta(Bbb.class);
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta);
        assertNotNull(primaryKeyDesc);
        assertEquals(1, primaryKeyDesc.getColumnNameList().size());
        assertEquals("ID", primaryKeyDesc.getColumnNameList().get(0));
        assertTrue(primaryKeyDesc.isIdentity());
    }

    @Test
    public void testId_sequence() throws Exception {
        EntityMeta entityMeta = entityMetaFactory.getEntityMeta(Ccc.class);
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta);
        assertNotNull(primaryKeyDesc);
        assertEquals(1, primaryKeyDesc.getColumnNameList().size());
        assertEquals("ID", primaryKeyDesc.getColumnNameList().get(0));
        assertFalse(primaryKeyDesc.isIdentity());
    }

    @Test
    public void testId_table() throws Exception {
        EntityMeta entityMeta = entityMetaFactory.getEntityMeta(Ddd.class);
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta);
        assertNotNull(primaryKeyDesc);
        assertEquals(1, primaryKeyDesc.getColumnNameList().size());
        assertEquals("ID", primaryKeyDesc.getColumnNameList().get(0));
        assertFalse(primaryKeyDesc.isIdentity());
    }

    @Test
    public void testId_auto() throws Exception {
        EntityMeta entityMeta = entityMetaFactory.getEntityMeta(Ddd.class);
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta);
        assertNotNull(primaryKeyDesc);
        assertEquals(1, primaryKeyDesc.getColumnNameList().size());
        assertEquals("ID", primaryKeyDesc.getColumnNameList().get(0));
        assertFalse(primaryKeyDesc.isIdentity());
    }

    @Test
    public void testCompositeId() throws Exception {
        EntityMeta entityMeta = entityMetaFactory.getEntityMeta(Fff.class);
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta);
        assertNotNull(primaryKeyDesc);
        assertEquals(2, primaryKeyDesc.getColumnNameList().size());
        assertEquals("ID1", primaryKeyDesc.getColumnNameList().get(0));
        assertEquals("ID2", primaryKeyDesc.getColumnNameList().get(1));
        assertFalse(primaryKeyDesc.isIdentity());
    }

    @Test
    public void testNoId() throws Exception {
        EntityMeta entityMeta = entityMetaFactory.getEntityMeta(Ggg.class);
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta);
        assertNull(primaryKeyDesc);
    }

    @Entity
    public static class Aaa {

        @Id
        public Integer id;
    }

    @Entity
    public static class Bbb {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Integer id;
    }

    @Entity
    public static class Ccc {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        public Integer id;
    }

    @Entity
    public static class Ddd {

        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        public Integer id;
    }

    @Entity
    public static class Eee {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        public Integer id;
    }

    @Entity
    public static class Fff {

        @Id
        public Integer id1;

        @Id
        public Integer id2;

    }

    @Entity
    public static class Ggg {

        public String value;
    }
}