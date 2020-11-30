package service;

import exceptions.DuplicateFoodException;
import exceptions.NegetiveFoodPriceException;
import exceptions.NoSuchFoodException;
import exceptions.NoSuchRestaurantException;
import model.entity.Food;
import model.entity.Restaurant;
import model.repository.*;

import java.util.List;

public class FoodService {

    FoodRepository foodRepository;
    RestaurantRepository restaurantRepository;

    public FoodService(FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;

    }

    public List<Food> getFoodsOfARestaurant(String restaurantName) throws NoSuchRestaurantException {
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if(restaurant == null){
            throw new NoSuchRestaurantException();
        }
        return restaurant.getFoods();
    }

    public Food getFoodByNameAndRestaurant(String foodName, String restaurantName) throws Exception {
        if(restaurantRepository.getRestaurantByName(restaurantName)== null){
            throw new NoSuchRestaurantException();
        }
        Food food = foodRepository.getFoodByNameAndRestaurant(foodName, restaurantName);
        if(food == null){
            throw new NoSuchFoodException();
        }
        return food;
    }

    public void addFood(Food food, Restaurant restaurant) throws Exception {
        if(food.getPrice() < 0)
        {
            throw new NegetiveFoodPriceException();
        }
        if(foodRepository.getFoodByNameAndRestaurant(food.getName(), restaurant.getName()) != null){
            throw new DuplicateFoodException();
        }
        foodRepository.addFood(food);
    }

}
