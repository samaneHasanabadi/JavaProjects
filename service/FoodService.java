package service;

import model.entity.Food;
import model.dao.ConnectionDao;
import model.dao.FoodDao;

import java.sql.Connection;
import java.util.ArrayList;

public class FoodService {

    FoodDao foodRepository = new FoodDao();
    private Connection connection = ConnectionDao.connectionRepository.getConnection();

    public void printFoodsOfARestaurant(String restaurantName) {
        ArrayList<Food> foods = foodRepository.getFoodsOFARestaurant(connection, restaurantName);
        for (Food food : foods) {
            System.out.println(food.getName() + ", price: " + food.getPrice() + ", type: " + food.getType());
        }
        System.out.println(
                "1.Enter food \n2.Enter \"back\" to return to previouse menu \n3.Enter \"basket\" to see your basket\"");
    }

    public Food getFoodByNameAndRestaurant(String foodName, String restaurantName) {
        Food food = foodRepository.getFoodWithPrice(connection, foodName, restaurantName);
        return food;
    }

    public Food getFoodWithPriceNameAndRestaurant(String foodName, String restaurantName){
        return foodRepository.getFoodWithPrice(connection, foodName, restaurantName);
    }

}
