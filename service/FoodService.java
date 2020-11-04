package service;

import model.entity.Food;
import model.Repository.ConnectionRepository;
import model.Repository.FoodRepository;

import java.sql.Connection;
import java.util.ArrayList;

public class FoodService {

    FoodRepository foodRepository = new FoodRepository();
    private Connection connection = ConnectionRepository.connectionRepository.getConnection();

    public ArrayList<Food> getFoodsOfARestaurant(String restaurantName) {
        ArrayList<Food> foods = foodRepository.getFoodsOFARestaurant(connection, restaurantName);
        return foods;
    }

    public Food getFoodByNameAndRestaurant(String foodName, String restaurantName) {
        Food food = foodRepository.getFoodWithPrice(connection, foodName, restaurantName);
        return food;
    }

    public Food getFoodWithPriceNameAndRestaurant(String foodName, String restaurantName){
        return foodRepository.getFoodWithPrice(connection, foodName, restaurantName);
    }

}
