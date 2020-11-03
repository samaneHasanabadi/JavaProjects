package model.dao;

import model.entity.Food;
import model.entity.FoodType;
import model.entity.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.RestaurantService;
import view.UserInteraction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RestaurantDao {
	Logger logger = LoggerFactory.getLogger(UserInteraction.class);
	public int addRestaurant(Connection connection, Restaurant restaurant) {
		Statement stm;
		try {
			stm = connection.createStatement();
			int row = stm.executeUpdate(
					"insert into restaurants (name, region, shipmentPrice) values" +
							" (\"" + restaurant.getName() + "\", "
							+ restaurant.getRegion() + ", " + restaurant.getShipmentPrice() + ")");
			if(row == 1){
				logger.debug("restaurant " + restaurant.getName() + " is added to database");
			}
			else {
				logger.error("restaurant " + restaurant.getName() + " is added to database");
			}
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int addFoodToRestaurant(Connection connection, ArrayList<Food> foods,
								   String restaurantName) {
		FoodDao foodRepository = new FoodDao();
		Statement stm;
		int rows = 0;
		for (int i = 0; i < foods.size(); i++) {
			foodRepository.addFood(connection, foods.get(i));
			try {
				stm = connection.createStatement();
				rows = stm.executeUpdate(
						"insert into restaurant_has_foods (foods_name, restaurants_name, price)" +
								" values (\""
								+ foods.get(i).getName() + "\", \"" + restaurantName + "\", " +
								foods.get(i).getPrice()
								+ ")");
				if(rows ==1 ){
					logger.debug("food " + foods.get(i).getName() + " of restaurant "+
							restaurantName+" is added to database");
				}else {
					logger.error("food " + foods.get(i).getName() + " of restaurant "+
							restaurantName+" is not added to database");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}

	public ArrayList<Restaurant> getRestaurantsInARegion(Connection connection, int region) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(
					"SELECT tb1.name, tb1.shipmentPrice, type, count(*) FROM restaurants as" +
							" tb1 join restaurant_has_foods on tb1.name=" +
							"restaurant_has_foods.restaurants_name join foods on " +
							"restaurant_has_foods.foods_name=foods.name where tb1.region= "
							+ region + " group by name, shipmentPrice, type");
			while (rs.next()) {
				Restaurant restaurant = new Restaurant(rs.getString("name"), region,
						rs.getInt(2));
				RestaurantService restaurantService = new RestaurantService();
				while (rs.getString("name").equals(restaurant.getName())) {
					restaurantService.addFoodType(restaurant ,FoodType.valueOf(rs.getString(3)));
					if (!rs.next()) {
						break;
					}
				}
				restaurants.add(restaurant);
				rs.previous();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurants;
	}

	public ArrayList<Restaurant> getRestaurantsInARegionWithType(Connection connection,
																 int region, String foodType) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(
					"SELECT tb1.name, tb1.shipmentPrice, type, region, count(*) FROM " +
							"restaurants as tb1 join restaurant_has_foods on " +
							"tb1.name=restaurant_has_foods.restaurants_name join foods on " +
							"restaurant_has_foods.foods_name=foods.name where tb1.region = "
							+ region + " and type=\"" + foodType + "\" group by name");
			while (rs.next()) {
				Restaurant restaurant = new Restaurant(rs.getString("name"), region,
						rs.getInt(2));
				RestaurantService restaurantService = new RestaurantService();
				restaurantService.addFoodType(restaurant, FoodType.valueOf(rs.getString(3)));
				restaurants.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurants;
	}

}
