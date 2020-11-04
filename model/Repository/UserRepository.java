package model.Repository;

import model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

import java.sql.*;

public class UserRepository {
	Logger logger = LoggerFactory.getLogger(UserInteraction.class);
	public int addUser(Connection connection, User user) {
		Statement stm;
		try {
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select * from users where mobilePhone=\"" +
					user.getMobileNumber()+"\"");
			int row = -1;
			if (!rs.next()) {
				try(PreparedStatement stmt = connection.prepareStatement("insert into " +
						"users (mobilePhone, regTime) values (?,?)");){
					Timestamp ts = new Timestamp(System.currentTimeMillis());
					stmt.setString(1, user.getMobileNumber());
					stmt.setTimestamp(2, ts);
					stmt.execute();
					logger.debug("user with mobile number " + user.getMobileNumber()+
							" is added to database");
				}
				catch(SQLException sqlEx){
					sqlEx.getMessage();
				}

			}
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public User getUserInfo(Connection connection, User user) {
		Statement stm;
		try {
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM OnlineFoodOrder.users where mobilePhone= \""
					+ user.getMobileNumber() +"\"");
			rs.next();
			if (rs.getString("name")!=null) {
				user.setName(rs.getString("name"));
			}
			if (rs.getString("postalCode")!=null) {
				user.setPostalCode(rs.getString("postalCode"));
			}
			if (rs.getString("address")!=null) {
				user.setAddress(rs.getString("address"));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public int SetUserInfo(Connection connection, User user) {
		Statement stm;
		try {
			stm = connection.createStatement();
			int	row = stm.executeUpdate("update users set name = \"" + user.getName() +
					"\", postalCode = \""
						+ user.getPostalCode() + "\" , address = \"" + user.getAddress() +
					"\" where mobilePhone = \""
						+ user.getMobileNumber() + "\"");
			if(row == 1){
				logger.debug("user information with mobile number " + user.getMobileNumber() +
						" is updated in database");
			}
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
