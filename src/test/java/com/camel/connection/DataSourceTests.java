package com.camel.connection;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/context-**.xml"})
public class DataSourceTests {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceTests.class);
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void testConnection() {
		try (SqlSession session = sqlSessionFactory.openSession();
				Connection con = dataSource.getConnection();
				) {
			logger.info(con.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
