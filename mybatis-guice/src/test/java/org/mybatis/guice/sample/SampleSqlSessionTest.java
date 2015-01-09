/*
 *    Copyright 2010-2012 The MyBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.guice.sample;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
// import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import org.mybatis.guice.sample.mapper.UserMapper;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

/**
 * Example of MyBatis-Guice basic integration usage.
 *
 * This is the recommended scenario.
 *
 * @version $Id$
 */
public class SampleSqlSessionTest extends SampleTestBase {
    @Override
    protected Module[] createModules() {
        return new Module[] {
            JdbcHelper.HSQLDB_IN_MEMORY_NAMED,
            new MyBatisModule() {
                @Override
                protected void initialize() {
                    bindDataSourceProviderType(PooledDataSourceProvider.class);
                    bindTransactionFactoryType(JdbcTransactionFactory.class);
                    addMapperClass(UserMapper.class);
                }

            },
            /* modules.add(new XMLMyBatisModule() {

                @Override
                protected void initialize() {
                    setEnvironmentId("test");
                    setClassPathResource("org/mybatis/guice/sample/mybatis-config.xml");
                }

            }, */
            new Module() {
                @Override
                public void configure(Binder binder) {
                    Names.bindProperties(binder, createTestProperties());
                }
            }
        };
    }
}
