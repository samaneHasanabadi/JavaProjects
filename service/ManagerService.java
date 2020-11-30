package service;

import model.dto.BasketDto;
import model.dto.OrderDto;
import model.entity.Restaurant;
import model.repository.ManagerRepository;

import java.util.*;

public class ManagerService {

    public boolean checkUsername(String username){
        if(username.equals("01Manager01")){
            return true;
        }
        return false;
    }

    public void reportUsersWithMonthRegisterationAndSumOfOrderPrice(){
        ManagerRepository managerRepository = new ManagerRepository();
        for (int i = 1; i < 13; i++){
            List<BasketDto> orders = managerRepository.
                    getUsersWithSumOfOrdersPrice();
            printUserReport(orders, i, 0, 100001);
            printUserReport(orders, i, 100000, 500001);
            printUserReport(orders, i, 500000, 1500001);
        }
    }

    public void printUserReport(List<BasketDto> orders, int month, int minSum, int maxSum){
        System.out.println("Month: " + month);
        System.out.println("Sum of Orders Prices between " + minSum +" and " + maxSum);
        orders.stream().filter(order->order.getSumOfOrdersPrice()>minSum)
                .filter(order->order.getSumOfOrdersPrice()<maxSum)
                .filter(order->order.getUser().getRegMonth()==month).map(BasketDto::getUser)
                .forEach(System.out::println);
    }

    public void reportRestaurantDeliveryIncomeAndFoodSoldNumber(){
        ManagerRepository managerRepository = new ManagerRepository();
        Map<Restaurant,List<OrderDto>> restaurants = managerRepository.
                getSumOfFoodSoldInEachRestaurant();
        printRestaurantDeliveryIncomeAndFoodReport(restaurants, 4, 0,
                10001);
        printRestaurantDeliveryIncomeAndFoodReport(restaurants, 4, 10000,
                20001);
        printRestaurantDeliveryIncomeAndFoodReport(restaurants, 4, 20000,
                100001);
        printRestaurantDeliveryIncomeAndFoodReport(restaurants, 5, 0,
                10001);
        printRestaurantDeliveryIncomeAndFoodReport(restaurants, 5, 10000,
                20001);
        printRestaurantDeliveryIncomeAndFoodReport(restaurants, 5, 20000,
                10000001);
    }

    public static void printRestaurantDeliveryIncomeAndFoodReport(Map<Restaurant,List<OrderDto>> restaurants,
                                                                  int region, int minDeliveryIncome,
                                                                  int maxDeliveryIncome){
        Optional<Restaurant> restaurant = getRestaurantWithMaxDeliveryIncome(restaurants, region, minDeliveryIncome, maxDeliveryIncome);
        restaurant.ifPresent(restaurant1 -> {
            System.out.println("region: "+ restaurant1.getRegion() +" name: "+ restaurant1.getName());
            getFoodReportOfTheRestaurant(restaurants, restaurant);
        });
    }

    private static void getFoodReportOfTheRestaurant(Map<Restaurant, List<OrderDto>> restaurants, Optional<Restaurant> restaurant) {
        restaurants.entrySet().stream().filter(entry -> entry.getKey().getName().equals(restaurant.get().getName())).forEach(entry -> System.out.println(entry.getValue().stream()
                       .max(Comparator.comparingLong(OrderDto::getSumOfFoodSold)).get().getFood().getName()));
    }

    private static Optional<Restaurant> getRestaurantWithMaxDeliveryIncome(Map<Restaurant, List<OrderDto>> restaurants, int region, int minDeliveryIncome, int maxDeliveryIncome) {
        return restaurants.keySet().stream().filter(restaurant -> restaurant.getRegion() == region).
                filter(restaurant -> restaurant.getNumberOfOrders() * restaurant.getDeliveryAmount() > minDeliveryIncome)
                .filter(restaurant -> restaurant.getNumberOfOrders() * restaurant.getDeliveryAmount() < maxDeliveryIncome).max(Comparator.comparingInt(Restaurant::getDeliveryIncome));
    }
}
