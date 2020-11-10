package service;

import model.entity.Food;
import model.entity.FoodType;
import model.entity.Restaurant;
import model.repository.RestaurantRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class RestaurantService {
    private RestaurantRepository restaurantRepository = new RestaurantRepository();
    private FoodService foodService = new FoodService();

    public void save (Restaurant restaurant){
        restaurantRepository.creat(restaurant);
    }

    public String[] subStringReturn(String line) {
        int endIndex = 0;
        while (line.charAt(endIndex) != ',') {
            endIndex++;
        }
        String s = line.substring(0, endIndex);
        String[] result = new String[2];
        result[0] = s;
        line = line.substring(endIndex + 2);
        result[1] = line;
        return result;
    }
    public void WriteRestaurantsInfoInDB() {
        try {
            Reader fileReader = new FileReader("//home//samane//Homwork7-OnlineFoodOrder" +
                    "//restaurants.txt");
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
            int restaurantCount = Integer.parseInt(line);
            line = bufferReader.readLine();
            for (int i = 0; i < restaurantCount; i++) {
                String[] result = subStringReturn(line);
                String restaurantName = result[0];
                line = result[1];
                result = subStringReturn(line);
                int foodsCount = Integer.parseInt(result[0]);
                line = result[1];
                result = subStringReturn(line);
                int region = Integer.parseInt(result[0]);
                line = result[1];
                int shipmentPrice = Integer.parseInt(line);
                Restaurant restaurant = new Restaurant(restaurantName, region, shipmentPrice);
                for (int j = 0; j < foodsCount; j++) {
                    line = bufferReader.readLine();
                    result = subStringReturn(line);
                    String foodName = result[0];
                    line = result[1];
                    result = subStringReturn(line);
                    int price = Integer.parseInt(result[0]);
                    line = result[1];
                    FoodType type = FoodType.valueOf(line);
                    Food food = new Food();
                    food.setName(foodName);
                    food.setType(type);
                    food.setPrice(price);
                    addFood(restaurant, food);
                }
                save(restaurant);
                line = bufferReader.readLine();
            }
            bufferReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFood(Restaurant restaurant,Food food) {
        for(int i = 0; i < restaurant.getFoods().size(); i++) {
            if(restaurant.getFoods().get(i).getName().equals(food.getName()))
                return;
        }
        foodService.addFood(food);
        restaurant.getFoods().add(food);
        addFoodType(restaurant, food.getType());
    }

    public void addFoodType(Restaurant restaurant,FoodType type) {
        if(!restaurant.getFoodTypes().contains(type)) {
            restaurant.getFoodTypes().add(type);
        }
    }

    public List<Restaurant> getRestaurantsInRegion(int region) {
        List<Restaurant> restaurants = restaurantRepository.getRestaurantsInARegion(region);
        return restaurants;
    }

    public List<Restaurant> getRestaurantsInRegionWithFoodType(int region, String foodType) {
        List<Restaurant> restaurants = restaurantRepository.
                getRestaurantsInARegionWithType(region, foodType);
        return restaurants;
    }
    public Restaurant getRestaurantByName(String restaurantName) {
        return restaurantRepository.getRestaurantByName(restaurantName);
    }
}
