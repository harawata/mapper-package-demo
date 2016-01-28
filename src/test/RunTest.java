/**
 *    Copyright 2009-2016 the original author or authors.
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
package test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import test.mapper.SampleMapper;
import java.sql.Connection;
import java.io.Reader;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;

public class RunTest {
	public static void main(String[] args) throws Exception {
		try(SqlSession session = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsReader("test/mapper/SqlMapConfig.xml"))
				.openSession()
		) {
			Connection con = session.getConnection();
			ScriptRunner runner = new ScriptRunner(con);
			runner.setLogWriter(null);
			Reader reader = Resources.getResourceAsReader("test/CreateDB.sql");
			runner.runScript(reader);
			reader.close();
			System.out.println(session.getMapper(SampleMapper.class).select(1));
			session.close();
		}
	}
}
