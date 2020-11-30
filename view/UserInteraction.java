package view;

import exceptions.NegativeRegionException;
import exceptions.NoSuchRestaurantException;
import model.entity.*;
import service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {
    RestaurantService restaurantService = new RestaurantService();
    FoodService foodService = new FoodService();
    User user;
    UserService userService = new UserService();
    String restaurantName = "";
    List<Restaurant> restaurants;

    public void userWorks(String mobileNumber) {
        try {
            //restaurantService.WriteRestaurantsInfoInDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        defineUserAndAddToDb(mobileNumber);
        System.out.println("please enter your region");
        String num = scanner.nextLine();
        int region = manageIntegerInputException(num, scanner);
        getRestaurantsInRegionHandling(region, scanner);
        printRestaurantAndCheck(restaurants, scanner);
        printMainMenu();
        String temp = scanner.nextLine();
        boolean bool = true;
        while (bool) {
            switch (temp) {
                case "irani":
                    try {
                        List<Restaurant> iraniRestaurants = restaurantService.getRestaurantsInRegionWithFoodType(region, "irani");
                        printRestaurants(iraniRestaurants);
                        temp = returnToPreviouseMenu(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        temp = returnToPreviouseMenu(scanner);
                    }
                    break;
                case "daryaee":
                    try {
                        List<Restaurant> daryaeeRestaurants = restaurantService.getRestaurantsInRegionWithFoodType(region, "daryaee");
                        printRestaurants(daryaeeRestaurants);
                        temp = returnToPreviouseMenu(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        temp = returnToPreviouseMenu(scanner);
                    }
                    break;
                case "fastfood":
                    try {
                        List<Restaurant> fastfoodRestaurants = restaurantService.getRestaurantsInRegionWithFoodType(region, "fastfood");
                        printRestaurants(fastfoodRestaurants);
                        temp = returnToPreviouseMenu(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        temp = returnToPreviouseMenu(scanner);
                    }
                    break;
                case "beinolmelali":
                    try {
                        List<Restaurant> beinolmelaliRestaurants = restaurantService.getRestaurantsInRegionWithFoodType(region, "beinolmelali");
                        printRestaurants(beinolmelaliRestaurants);
                        temp = returnToPreviouseMenu(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        temp = returnToPreviouseMenu(scanner);
                    }
                    break;
                case "basket":
                    showBasket(scanner);
                    printRestaurants(restaurants);
                    printSecondMenu();
                    temp = scanner.nextLine();
                    break;
                case "exit":
                    bool = false;
                    break;
                default:
                    try {
                        restaurantName = temp;
                        List<Food> foods = foodService.getFoodsOfARestaurant(temp);
                        boolean checker = true;
                        while (checker) {
                            prinFoods(foods);
                            printFoodMenu();
                            temp = scanner.nextLine();
                            switch (temp) {
                                case "back":
                                    printRestaurants(restaurants);
                                    ;
                                    printSecondMenu();
                                    temp = scanner.nextLine();
                                    checker = false;
                                    break;
                                case "basket":
                                    showBasket(scanner);
                                    printRestaurants(restaurants);
                                    printSecondMenu();
                                    temp = scanner.nextLine();
                                    checker = false;
                                    break;
                                default:
                                    String foodName = temp;
                                    System.out.println("Please enter number of food");
                                    num = scanner.nextLine();
                                    int number = manageIntegerInputException(num, scanner);
                                    try {
                                        Food food = foodService.getFoodByNameAndRestaurant(foodName, restaurantName);
                                        userService.addFoodToBasket(user, food, number,
                                                restaurantService.getRestaurantByName(restaurantName));
                                        System.out.println(foodName + " is added to your basket successfully!");
                                        printRestaurants(restaurants);
                                        printSecondMenu();
                                        checker = false;
                                        temp = scanner.nextLine();
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                        printRestaurants(restaurants);
                                        printSecondMenu();
                                        temp = scanner.nextLine();
                                    }
                            }
                        }
                    } catch (NoSuchRestaurantException e) {
                        System.out.println(e.getMessage());
                        printRestaurants(restaurants);
                        printSecondMenu();
                        temp = scanner.nextLine();
                    }
            }
        }

    }

    private void getRestaurantsInRegionHandling(int region, Scanner scanner) {
        boolean flag = false;
        while (!flag) {
            try {
                restaurants = restaurantService.getRestaurantsInRegion(region);
                flag = true;
            } catch (NegativeRegionException e) {
                System.out.println(e.getMessage() + "/nplease enter the region again");
                String num = scanner.nextLine();
                region = manageIntegerInputException(num, scanner);
            }
        }
    }

    private void defineUserAndAddToDb(String mobileNumber) {
        user = new User();
        user.setMobileNumber(mobileNumber);
        Date date = new Date();
        user.setRegMonth(date.getMonth());
        user = userService.addUserToDB(user);
    }

    private void printRestaurants(List<Restaurant> restaurants) {
        restaurants.stream().forEach(restaurant -> {
            System.out.println("restaurant name: " + restaurant.getName() + ", Shipment price: "
                    + restaurant.getDeliveryAmount() + ", Food Types: ");
            restaurant.getFoodTypes().stream().forEach(ft -> System.out.print(ft + ", "));
            System.out.println();
        });
    }

    private void printRestaurantAndCheck(List<Restaurant> restaurants, Scanner scanner) {
        while (restaurants.size() == 0) {
            System.out.println("There is no restaurant in this region please enter a valid region number" +
                    "or another region");
            String num = scanner.nextLine();
            int region = manageIntegerInputException(num, scanner);
            getRestaurantsInRegionHandling(region, scanner);
        }
        printRestaurants(restaurants);
    }

    private void prinFoods(List<Food> foods) {
        foods.stream().forEach(food -> System.out.println(food.getName() + ", price: " +
                food.getPrice() + ", type: " + food.getType()));
    }

    private void printFoodMenu() {
        System.out.println(
                "1.Enter food \n2.Enter \"back\" to return to previouse menu \n3.Enter" +
                        " \"basket\" to see your basket\"");
    }

    private void printSecondMenu() {
        System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" " +
                "to see your basket\n4.Enter \"exit\" to exit");
    }

    private void printMainMenu() {
        System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" " +
                "to see your basket \n4.Enter \"exit\" to exit");
    }

    public String returnToPreviouseMenu(Scanner scanner) {
        System.out.println(
                "1.Enter restaurant name \n2.Enter \"back\" to return to previouse menu \n3.Enter" +
                        " \"basket\" to see your basket\"");
        String choose = scanner.nextLine();
        String temp;
        if (choose.equals("back")) {
            printRestaurants(restaurants);
            System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" " +
                    "to see your basket");
            temp = scanner.nextLine();
        } else {
            temp = choose;
        }
        return temp;
    }

    public void showBasket(Scanner scanner) {
        if (user.getBasket().getItems().isEmpty()) {
            System.out.println("Your basket is empty");
            return;
        }
        printBasketItems(user);
        System.out.println("Whole Price: " + user.getBasket().getWholePrice());
        System.out.println("1.modify basket \n2.back to perviouse menu\n3.Finale your order");
        String num = scanner.nextLine();
        int choose = manageIntegerInputException(num, scanner);
        switch (choose) {
            case 1:
                modifyBasket(user, scanner);
                printRestaurants(restaurants);
            case 2:
                return;
            case 3:
                userService.setOrder(user);
                userService.saveInvoice(user);
                if (user.getName() == null) {
                    System.out.println("Enter your name");
                    user.setName(scanner.nextLine());
                    System.out.println("Enter your address");
                    user.setAddress(scanner.nextLine());
                    System.out.println("Enter your postal code");
                    user.setPostalCode(scanner.nextLine());
                    userService.setUserInfo(user);
                }
                user = userService.getUserInfo(user);
                userService.clearBasket(user);
                System.out.println("Your Order is ready");
                break;
            default:
                System.out.println("please enter the right option");
                showBasket(scanner);
        }
    }

    private void printBasketItems(User user) {
        user.getBasket().getItems().keySet().stream().forEach(basketItem -> System.out.println
                (basketItem.getName() + ", price: " + basketItem.getPrice() + ", number:" +
                        " " + user.getBasket().getItems().get(basketItem)));
    }

    private int manageIntegerInputException(String num, Scanner scanner) {
        int choose = 0;
        while (choose == 0) {
            try {
                choose = Integer.parseInt(num);
                if (choose == 0) {
                    System.out.println("please enter a valid number");
                    num = scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid number");
                num = scanner.nextLine();
            }
        }
        return choose;
    }

    public void modifyBasket(User user, Scanner scanner) {
        System.out.println("1.Remove a food \n2.Modify food number");
        String num = scanner.nextLine();
        int choose = manageIntegerInputException(num, scanner);
        switch (choose) {
            case 1:
                System.out.println("Enter food name");
                String foodName = scanner.nextLine();
                try {
                    Food food = foodService.getFoodByNameAndRestaurant(foodName, restaurantName);
                    userService.removeFoodFromBasket(user, food);
                    System.out.println(foodName + " is removed from your basket successfully!");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                System.out.println("Enter food name");
                foodName = scanner.nextLine();
                try {
                    Food food = foodService.getFoodByNameAndRestaurant(foodName, restaurantName);
                    System.out.println("Enter new number");
                    String num2 = scanner.nextLine();
                    int newNumber = manageIntegerInputException(num2, scanner);
                    userService.modifyFoodNumberInBasket(user, food, newNumber);
                    System.out.println("Your change is applied");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            default:
                System.out.println("please enter the right option");
                modifyBasket(user, scanner);
                return;
        }
        showBasket(scanner);
    }
}
