package service;

import model.entity.Food;
import model.repository.*;

import java.util.List;

public class FoodService {

    FoodRepository foodRepository = new FoodRepository();

    public List<Food> getFoodsOfARestaurant(String restaurantName) {
        List<Food> foods = foodRepository.getFoodsOFARestaurant(restaurantName);
        return foods;
    }

    public Food getFoodByNameAndRestaurant(String foodName, String restaurantName) {
        Food food = foodRepository.getFoodByNameAndRestaurant(foodName, restaurantName);
        return food;
    }

    public Food getFoodWithPriceNameAndRestaurant(String foodName, String restaurantName){
        return foodRepository.getFoodByNameAndRestaurant(foodName, restaurantName);
    }

    public void addFood(Food food){
        foodRepository.addFood(food);
    }

}
