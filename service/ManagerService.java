package service;

import model.dto.OrderDto;
import model.entity.Restaurant;
import model.entity.User;
import model.repository.ManagerRepository;

import javax.persistence.SecondaryTable;
import java.util.*;
import java.util.stream.Collectors;

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
            List<User> users = managerRepository.
                    getUsersWithMonthRegisterationAndSumOfOrderPrice();
            printUserReport(users, i, 0, 100001);
            printUserReport(users, i, 100000, 500001);
            printUserReport(users, i, 500000, 1500001);
        }
    }

    public void printUserReport(List<User> users, int month, int minSum, int maxSum){
        System.out.println("Month: " + month);
        System.out.println("Sum of Orders Prices between " + minSum +" and " + maxSum);
        users.stream().filter(a->a.getOrdersSumPrice()>minSum).filter(a->a.getOrdersSumPrice()<maxSum)
                .filter(a->a.getRegMonth()==month).forEach(System.out::println);
    }

    public void reportRestaurantPeykIncomeAndFoodSoldNumber(){
        ManagerRepository managerRepository = new ManagerRepository();
        Map<Restaurant,List<OrderDto>> restaurants = managerRepository.
                getRestaurantsWithPeykIncomeAndFoodSold();
        printRestaurantPeykIncomeAndFoodReport(restaurants, 4, 0,
                10001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 4, 10000,
                20001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 4, 20000,
                100001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 5, 0,
                10001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 5, 10000,
                20001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 5, 20000,
                100001);
    }

    public static void printRestaurantPeykIncomeAndFoodReport(Map<Restaurant,List<OrderDto>> restaurants,
                                                              int region, int minPeykIncome,
                                                              int maxPeykIncome){
        restaurants.entrySet().stream().filter(a->a.getKey().getRegion()==region).
                filter(a->a.getKey().getOrders().size()*a.getKey().getShipmentPrice()>minPeykIncome)
                .filter(a->a.getKey().getOrders().size()*a.getKey().getShipmentPrice()<maxPeykIncome).
                peek(a->System.out.println("region: "+ a.getKey().getRegion() +" name: "+ a.getKey().
                        getName())).forEach(a-> System.out.println(a.getValue().stream()
                        .max(Comparator.comparingLong(b->b.getNumberSold())).get().getFood().getName()));
    }
}
