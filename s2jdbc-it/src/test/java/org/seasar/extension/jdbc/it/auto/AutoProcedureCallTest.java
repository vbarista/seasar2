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
package org.seasar.extension.jdbc.it.auto;

import java.util.List;

import org.junit.runner.RunWith;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.annotation.InOut;
import org.seasar.extension.jdbc.annotation.Out;
import org.seasar.extension.jdbc.annotation.ResultSet;
import org.seasar.extension.jdbc.it.entity.Department;
import org.seasar.extension.jdbc.it.entity.Employee;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;

import static junit.framework.Assert.*;

/**
 * @author taedium
 * 
 */
@RunWith(Seasar2.class)
@Prerequisite("#ENV not in {'hsqldb', 'h2'}")
public class AutoProcedureCallTest {

    private JdbcManager jdbcManager;

    /**
     * 
     * @throws Exception
     */
    public void testParameter_noneTx() throws Exception {
        jdbcManager.call("PROC_NONE_PARAM").execute();
    }

    /**
     * 
     * @throws Exception
     */
    public void testParameter_simpleTypeTx() throws Exception {
        jdbcManager.call("PROC_SIMPLETYPE_PARAM", 1).execute();
    }

    /**
     * 
     * @throws Exception
     */
    public void testParameter_dtoTx() throws Exception {
        MyDto dto = new MyDto();
        dto.param1 = 3;
        dto.param2 = 5;
        jdbcManager.call("PROC_DTO_PARAM", dto).execute();
        assertEquals(new Integer(3), dto.param1);
        assertEquals(new Integer(8), dto.param2);
        assertEquals(new Integer(3), dto.param3);
    }

    /**
     * 
     * @throws Exception
     */
    public void testParameter_resultSetTx() throws Exception {
        ResultSetDto dto = new ResultSetDto();
        dto.employeeId = 10;
        jdbcManager.call("PROC_RESULTSET", dto).execute();
        List<Employee> employees = dto.employees;
        assertNotNull(employees);
        assertEquals(4, employees.size());
        assertEquals("ADAMS", employees.get(0).employeeName);
        assertEquals("JAMES", employees.get(1).employeeName);
        assertEquals("FORD", employees.get(2).employeeName);
        assertEquals("MILLER", employees.get(3).employeeName);
    }

    /**
     * 
     * @throws Exception
     */
    public void testParameter_resultSetOutTx() throws Exception {
        ResultSetOutDto dto = new ResultSetOutDto();
        dto.employeeId = 10;
        jdbcManager.call("PROC_RESULTSET_OUT", dto).execute();
        List<Employee> employees = dto.employees;
        assertNotNull(employees);
        assertEquals(4, employees.size());
        assertEquals("ADAMS", employees.get(0).employeeName);
        assertEquals("JAMES", employees.get(1).employeeName);
        assertEquals("FORD", employees.get(2).employeeName);
        assertEquals("MILLER", employees.get(3).employeeName);
        assertEquals(14, dto.employeeCount);
    }

    /**
     * 
     * @throws Exception
     */
    public void testParameter_resultSetUpdateTx() throws Exception {
        ResultSetUpdateDto dto = new ResultSetUpdateDto();
        dto.employeeId = 10;
        jdbcManager.call("PROC_RESULTSET_UPDATE", dto).execute();
        List<Employee> employees = dto.employees;
        assertNotNull(employees);
        assertEquals(4, employees.size());
        assertEquals("ADAMS", employees.get(0).employeeName);
        assertEquals("JAMES", employees.get(1).employeeName);
        assertEquals("FORD", employees.get(2).employeeName);
        assertEquals("MILLER", employees.get(3).employeeName);
        String departmentName =
            jdbcManager
                .selectBySql(
                    String.class,
                    "select department_name from Department where department_id = ?",
                    1)
                .getSingleResult();
        assertEquals("HOGE", departmentName);
    }

    /**
     * 
     * @throws Exception
     */
    public void testParameter_resultSetsTx() throws Exception {
        ResultSetsDto dto = new ResultSetsDto();
        dto.employeeId = 10;
        dto.departmentId = 2;
        jdbcManager.call("PROC_RESULTSETS", dto).execute();
        List<Employee> employees = dto.employees;
        assertNotNull(employees);
        assertEquals(4, employees.size());
        assertEquals("ADAMS", employees.get(0).employeeName);
        assertEquals("JAMES", employees.get(1).employeeName);
        assertEquals("FORD", employees.get(2).employeeName);
        assertEquals("MILLER", employees.get(3).employeeName);
        List<Department> departments = dto.departments;
        assertNotNull(departments);
        assertEquals(2, departments.size());
        assertEquals("SALES", departments.get(0).departmentName);
        assertEquals("OPERATIONS", departments.get(1).departmentName);
    }

    /**
     * 
     * @throws Exception
     */
    public void testParameter_resultSetsUpdatesOutTx() throws Exception {
        ResultSetsUpdatesOutDto dto = new ResultSetsUpdatesOutDto();
        dto.employeeId = 10;
        dto.departmentId = 2;
        jdbcManager.call("PROC_RESULTSETS_UPDATES_OUT", dto).execute();
        List<Employee> employees = dto.employees;
        assertNotNull(employees);
        assertEquals(4, employees.size());
        assertEquals("ADAMS", employees.get(0).employeeName);
        assertEquals("JAMES", employees.get(1).employeeName);
        assertEquals("FORD", employees.get(2).employeeName);
        assertEquals("MILLER", employees.get(3).employeeName);
        List<Department> departments = dto.departments;
        assertNotNull(departments);
        assertEquals(2, departments.size());
        assertEquals("SALES", departments.get(0).departmentName);
        assertEquals("OPERATIONS", departments.get(1).departmentName);
        String street =
            jdbcManager.selectBySql(
                String.class,
                "select street from Address where address_id = ?",
                1).getSingleResult();
        assertEquals("HOGE", street);
        street =
            jdbcManager.selectBySql(
                String.class,
                "select street from Address where address_id = ?",
                2).getSingleResult();
        assertEquals("FOO", street);
        assertEquals(14, dto.employeeCount);
    }

    /**
     * 
     * @author taedium
     * 
     */
    public static class MyDto {

        /** */
        public Integer param1;

        /** */
        @InOut
        public Integer param2;

        /** */
        @Out
        public Integer param3;
    }

    /**
     * 
     * @author taedium
     * 
     */
    public static class ResultSetDto {

        /** */
        @ResultSet
        public List<Employee> employees;

        /** */
        public int employeeId;
    }

    /**
     * 
     * @author taedium
     * 
     */
    public static class ResultSetOutDto {

        /** */
        @ResultSet
        public List<Employee> employees;

        /** */
        public int employeeId;

        /** */
        @Out
        public int employeeCount;

    }

    /**
     * 
     * @author taedium
     * 
     */
    public static class ResultSetUpdateDto {

        /** */
        @ResultSet
        public List<Employee> employees;

        /** */
        public int employeeId;

    }

    /**
     * 
     * @author taedium
     * 
     */
    public static class ResultSetsDto {

        /** */
        @ResultSet
        public List<Employee> employees;

        /** */
        @ResultSet
        public List<Department> departments;

        /** */
        public int employeeId;

        /** */
        public int departmentId;
    }

    /**
     * 
     * @author taedium
     * 
     */
    public static class ResultSetsUpdatesOutDto {

        /** */
        @ResultSet
        public List<Employee> employees;

        /** */
        @ResultSet
        public List<Department> departments;

        /** */
        public int employeeId;

        /** */
        public int departmentId;

        /** */
        @Out
        public int employeeCount;
    }

}