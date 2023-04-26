package com.mindtree.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mindtree.exception.UtilityException;

public class DBConnection {
	
	private static final String url = "jdbc:mysql://localhost:3306/furnituredb";
	private static final String userName = "root";
	private static final String password = "Welcome123";


	
	
	public static Connection connection() throws UtilityException
	{
		Connection connection=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException |SQLException e) {
			e.printStackTrace();
			throw new UtilityException();
		}

		return connection;
	}
	
	

}
