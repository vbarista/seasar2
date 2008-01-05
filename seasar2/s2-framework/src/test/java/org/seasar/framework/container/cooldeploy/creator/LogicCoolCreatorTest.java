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
package org.seasar.framework.container.cooldeploy.creator;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.cooldeploy.creator.logic.EeeLogic;
import org.seasar.framework.container.cooldeploy.creator.logic.impl.EeeLogicImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;

/**
 * @author higa
 * 
 */
public class LogicCoolCreatorTest extends S2FrameworkTestCase {

    /**
     * 
     */
    public LogicCoolCreatorTest() {
        setWarmDeploy(false);
    }

    protected void setUp() {
        include("LogicCoolCreatorTest.dicon");
    }

    /**
     * @throws Exception
     */
    public void testAll() throws Exception {
        assertTrue(getContainer().hasComponentDef(EeeLogic.class));
        ComponentDef cd = getComponentDef(EeeLogic.class);
        assertEquals(EeeLogicImpl.class, cd.getComponentClass());
    }
}