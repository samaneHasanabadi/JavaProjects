package service;

import exceptions.*;
import model.entity.Food;
import model.entity.FoodType;
import model.entity.Restaurant;
import model.repository.RestaurantRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class RestaurantService {
    private RestaurantRepository restaurantRepository = new RestaurantRepository();
    private FoodService foodService = new FoodService();

    public void save (Restaurant restaurant) throws Exception{
        if(restaurant.getDeliveryAmount() < 0){
            throw new NegativeDeliveryAmountException();
        }
        if(restaurant.getRegion() < 0){
            throw  new NegativeRegionException();
        }
        if(restaurantRepository.getRestaurantByName(restaurant.getName()) != null){
            throw new DuplicateRestaurantException();
        }
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
    public void WriteRestaurantsInfoInDB() throws Exception {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFood(Restaurant restaurant,Food food) throws Exception {
        foodService.addFood(food, restaurant);
        restaurant.getFoods().add(food);
        try {
            addFoodType(restaurant, food.getType());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void addFoodType(Restaurant restaurant,FoodType type) throws DuplicateFoodTypeException {
        if(!restaurant.getFoodTypes().contains(type)) {
            restaurant.getFoodTypes().add(type);
        }else{
            throw new DuplicateFoodTypeException();
        }
    }

    public List<Restaurant> getRestaurantsInRegion(int region) throws NegativeRegionException {
        if(region < 0){
            throw new NegativeRegionException();
        }
        List<Restaurant> restaurants = restaurantRepository.
                getRestaurantByRegionAndFoodType(region, null);
        return restaurants;
    }

    public List<Restaurant> getRestaurantsInRegionWithFoodType(int region, String foodType) throws Exception {
        if(region < 0){
            throw new NegativeRegionException();
        }
        try{
            FoodType.valueOf(foodType);
        }catch (Exception e){
            throw new NoSuchFoodTypeException();
        }
        List<Restaurant> restaurants = restaurantRepository.
                getRestaurantByRegionAndFoodType(region, foodType);
        return restaurants;
    }
    public Restaurant getRestaurantByName(String restaurantName) throws NoSuchRestaurantException {
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if(restaurant == null){
            throw new NoSuchRestaurantException();
        }
        return restaurant;
    }
}
