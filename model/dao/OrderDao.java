package model.dao;

import model.entity.Food;
import model.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

public class OrderDao {
	Logger logger = LoggerFactory.getLogger(UserInteraction.class);
	public int addOrder(Connection connection, Order order) {
		Statement stm;
		try {
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("SELECT orderNumber FROM orders order by orderNumber" +
					" desc limit 1");
			int orderNumber = 1;
			if(rs.next())
				orderNumber = rs.getInt(1) + 1;
			int row = stm.executeUpdate("insert into orders (OrderNumber, restaurants_name," +
					" users_mobilePhone1) values ("
					+ orderNumber + ", \"" + order.getRestaurantName() + "\", \"" + order.getUserPhoneNumber() +
					"\")");
			if( row ==1){
				logger.debug("order number " + orderNumber + " is added to database");
			}
			else{
				logger.error("order number " + orderNumber + " is not added to database");
			}
			Set<Food> foods = order.getItems().keySet();
			Iterator<Food> it = foods.iterator();
			while (it.hasNext()) {
				Food food = (Food) it.next();
				row = stm.executeUpdate(
						"insert into order_has_food (foods_name, orders_OrderNumber," +
								" orders_restaurants_name, orders_users_mobilePhone1, numbers)" +
								" values (\""
								+ food.getName() + "\"," + orderNumber + ", \"" + order.getRestaurantName()
								+ "\", \""
								+ order.getUserPhoneNumber()+ "\", "+ order.getItems().get(food) + ")");
				if(row == 1){
					logger.debug("food " + food.getName() + " of order number " + orderNumber +
							" is added to order foods in database");
				}else {
					logger.debug("food " + food.getName() + " of order number " + orderNumber +
							" is  not added to order foods in database");
				}
			}
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
