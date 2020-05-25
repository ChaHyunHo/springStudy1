package com.camel.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCTests {
	private static final Logger logger = LoggerFactory.getLogger(JDBCTests.class);
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3307/"
			+ "test?useSSL=false&serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PW = "root";

	@Test
	public void testConnection() throws Exception {
		
		Class.forName(DRIVER);
		
		try(Connection con = DriverManager.getConnection(URL,USER,PW)) {
			logger.info(con.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
