package service;

import model.entity.Food;
import model.dao.ConnectionDao;
import model.dao.FoodDao;

import java.sql.Connection;
import java.util.ArrayList;

public class FoodService {

    FoodDao foodDao = new FoodDao();
    private Connection connection = ConnectionDao.connectionRepository.getConnection();

    public ArrayList<Food> getFoodsOfARestaurant(String restaurantName) {
        ArrayList<Food> foods = foodDao.getFoodsOFARestaurant(connection, restaurantName);
        return foods;
    }

    public Food getFoodByNameAndRestaurant(String foodName, String restaurantName) {
        Food food = foodDao.getFoodWithPrice(connection, foodName, restaurantName);
        return food;
    }

    public Food getFoodWithPriceNameAndRestaurant(String foodName, String restaurantName){
        return foodDao.getFoodWithPrice(connection, foodName, restaurantName);
    }

}
