package model.repository;

import model.entity.Food;
import model.entity.FoodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodRepository {
	Logger logger = LoggerFactory.getLogger(UserInteraction.class);
	public int addFood(Connection connection, Food food) {
		Statement stm;
		try {
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM OnlineFoodOrder.foods where name=\""+food.getName()+"\"");
			if(rs.next()) {
				logger.info("the food "+ food.getName() +" is already exists");
				return -1;
			}
			int row = stm.executeUpdate(
					"insert into foods (name, type) values (\"" + food.getName() + "\", \"" + food.getType() + "\")");
			if(row == 1)
			{
				logger.trace("food " + food.getName() + " is added to database");
			}else {
				logger.error("food "+ food.getName() + " is not added to database");
			}
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ArrayList<Food> getFoodsOFARestaurant(Connection connection, String restaurantName) {
		ArrayList<Food> foods = new ArrayList<Food>();
		Statement stm;
		try {
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(
					"SELECT tb1.*, price from foods as tb1 join restaurant_has_foods on name=foods_name where restaurants_name=\""
							+ restaurantName + "\"");
			while (rs.next()) {
				Food food = new Food(rs.getString("name"), rs.getInt("price"), FoodType.valueOf(rs.getString("type")));
				foods.add(food);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foods;
	}

	public Food getFoodWithPrice(Connection connection, String foodName, String restaurantName) {
		Food food = null;
		Statement stm;
		try {
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(
					"SELECT name, type, price FROM foods join restaurant_has_foods on foods.name=restaurant_has_foods.foods_name where name = \""
							+ foodName + "\" and restaurants_name = \"" + restaurantName +"\"");
			while (rs.next()) {
				food = new Food(rs.getString("name"), rs.getInt("price"), FoodType.valueOf(rs.getString("type")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return food;
	}

}
