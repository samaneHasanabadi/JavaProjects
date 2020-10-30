package service;

import model.entity.Food;
import model.entity.FoodType;
import model.entity.Restaurant;
import model.repository.ConnectionRepository;
import model.repository.RestaurantRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;

public class RestaurantService {
    private RestaurantRepository restaurantRepository = new RestaurantRepository();
    private Connection connection = ConnectionRepository.connectionRepository.getConnection();

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
            Reader fileReader = new FileReader("//home//samane//Homwork7-OnlineFoodOrder//restaurant.txt");
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
                    addFood(restaurant, foodName, price, type);
                }
                restaurantRepository.addRestaurant(connection, restaurant);
                restaurantRepository.addFoodToRestaurant(connection, restaurant.getFoods(), restaurantName);
                line = bufferReader.readLine();
            }
            bufferReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFood(Restaurant restaurant,String name, int price, FoodType type) {
        for(int i = 0; i < restaurant.getFoods().size(); i++) {
            if(restaurant.getFoods().get(i).getName().equals(name))
                return;
        }
        Food food = new Food(name, price, type);
        restaurant.getFoods().add(food);
        if(!restaurant.getFoodTypes().contains(type)) {
            restaurant.getFoodTypes().add(type);
        }
    }

    public void addFoodType(Restaurant restaurant,FoodType type) {
        if(!restaurant.getFoodTypes().contains(type)) {
            restaurant.getFoodTypes().add(type);
        }
    }

    public void getRestaurantsInRegionAndPrint(int region) {
        ArrayList<Restaurant> restaurants = restaurantRepository.getRestaurantsInARegion(connection, region);
        for (Restaurant restaurant : restaurants) {
            System.out.println("restaurant name: " + restaurant.getName() + ", Shipment price: "
                    + restaurant.getShipmentPrice() + ", model.entity.Food Types: ");
            for (FoodType ft : restaurant.getFoodTypes()) {
                System.out.print(ft + ", ");
            }
            System.out.println();
        }
    }

    public void getRestaurantsInRegionWithFoodTypeAndPrint(int region, String foodType) {
        ArrayList<Restaurant> restaurants = restaurantRepository.getRestaurantsInARegionWithType(connection, region,
                foodType);
        for (Restaurant restaurant : restaurants) {
            System.out.println("restaurant name: " + restaurant.getName() + ", Shipment price: "
                    + restaurant.getShipmentPrice() + ", model.entity.Food Types: ");
            for (FoodType ft : restaurant.getFoodTypes()) {
                System.out.print(ft + ", ");
            }
            System.out.println();
        }
    }
}
