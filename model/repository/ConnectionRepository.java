package model.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionRepository {
	Logger logger = LoggerFactory.getLogger(UserInteraction.class);
	private Connection con;
	public static ConnectionRepository connectionRepository = new ConnectionRepository();
	
	private ConnectionRepository() {
		try {
			//Class.forName("com.mysql.jdbc.Driver")
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineFoodOrder?useSSL=false", "root", "123");
			logger.trace("Connection is made");
		} catch ( SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return con;
	}

}
