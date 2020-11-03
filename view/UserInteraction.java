package view;

import model.entity.*;
import service.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInteraction {
	RestaurantService restaurantService = new RestaurantService();
	FoodService foodService = new FoodService();
	User user;
	UserService userService = new UserService();
	String restaurantName = "";
	OrderService orderService = new OrderService();
	ArrayList<Restaurant> restaurants;
	//Logger logger = LoggerFactory.getLogger(Main.class);
	public void userWorks (String mobileNumber) {
		//restaurantService.WriteRestaurantsInfoInDB();
		Scanner scanner = new Scanner(System.in);
		user = new User();
		user.setMobileNumber(mobileNumber);
		userService.addUserToDB(user);
		System.out.println("please enter your region");
		String num = scanner.nextLine();
		int region = manageIntegerInputException(num, scanner);
		restaurants = restaurantService.getRestaurantsInRegion(region);
		printRestaurantAndCheck(restaurants, scanner);
		printMainMenu();
		String temp = scanner.nextLine();
		boolean bool = true;
		while (bool) {
			switch (temp) {
			case "irani":
				ArrayList<Restaurant> iraniRestaurants =
						restaurantService.getRestaurantsInRegionWithFoodType(region, "irani");
				printRestaurants(iraniRestaurants);
				temp = returnToPreviouseMenu(scanner, region);
				break;
			case "daryaee":
				ArrayList<Restaurant> daryaeeRestaurants =
						restaurantService.getRestaurantsInRegionWithFoodType(region, "daryaee");
				printRestaurants(daryaeeRestaurants);
				temp = returnToPreviouseMenu(scanner, region);
				break;
			case "fastfood":
				ArrayList<Restaurant> fastfoodRestaurants =
						restaurantService.getRestaurantsInRegionWithFoodType(region, "fastfood");
				printRestaurants(fastfoodRestaurants);
				temp = returnToPreviouseMenu(scanner, region);
				break;
			case "beinolmelali":
				ArrayList<Restaurant> beinolmelaliRestaurants =
						restaurantService.getRestaurantsInRegionWithFoodType(region, "beinolmelali");
				printRestaurants(beinolmelaliRestaurants);
				temp = returnToPreviouseMenu(scanner, region);
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
				restaurantName = temp;
				ArrayList<Food> foods = foodService.getFoodsOfARestaurant(temp);
				if(foods.size() == 0){
					System.out.println("invalid input");
					printRestaurants(restaurants);;
					printSecondMenu();
					temp = scanner.nextLine();
				}else {
					boolean checker = true;
					while (checker) {
						prinFoods(foods);
						printFoodMenu();
						temp = scanner.nextLine();
						switch (temp) {
							case "back":
								printRestaurants(restaurants);;
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
								String finalTemp = temp;
								if (foods.stream().anyMatch(a -> a.getName().equals(finalTemp))) {
									System.out.println("Please enter number of food");
									num = scanner.nextLine();
									int number = manageIntegerInputException(num, scanner);
									Food food = foodService.getFoodByNameAndRestaurant(temp, restaurantName);
									userService.addFoodToBasket(user, food, number, restaurantName);
									printRestaurants(restaurants);
									printSecondMenu();
									checker = false;
									temp = scanner.nextLine();
								} else {
									System.out.println("invalid input");
								}
						}
					}
				}
			}
		}

	}

	private void printRestaurants(ArrayList<Restaurant> restaurants) {
		for (Restaurant restaurant : restaurants) {
			System.out.println("restaurant name: " + restaurant.getName() + ", Shipment price: "
					+ restaurant.getShipmentPrice() + ", Food Types: ");
			for (FoodType ft : restaurant.getFoodTypes()) {
				System.out.print(ft + ", ");
			}
			System.out.println();
		}
	}

	private void printRestaurantAndCheck(ArrayList<Restaurant> restaurants, Scanner scanner) {
		while (restaurants.size()==0) {
			System.out.println("There is no restaurant in this region please enter a valid region number" +
					"or another region");
			String num = scanner.nextLine();
			int region = manageIntegerInputException(num, scanner);
			restaurants = restaurantService.getRestaurantsInRegion(region);
		}
		printRestaurants(restaurants);
	}

	private void prinFoods(ArrayList<Food> foods) {
		for (Food food : foods) {
			System.out.println(food.getName() + ", price: " + food.getPrice() + ", type: "
					+ food.getType());
		}
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

	public String returnToPreviouseMenu(Scanner scanner, int region) {
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
		for (Food basketItem : user.getBasket().getItems().keySet()) {
			System.out.println(basketItem.getName() + ", price: " + basketItem.getPrice() + ", number:" +
					" " + user.getBasket().getItems().get(basketItem));
		}
		System.out.println("Whole Price: " + user.getBasketPrice());
		System.out.println("1.modify basket \n2.back to perviouse menu\n3.Finale your order");
		String num = scanner.nextLine();
		int choose = manageIntegerInputException(num, scanner);
		switch(choose) {
		case 1:
			modifyBasket(user, scanner);
		case 2:
			return;
		case 3:
			user = userService.getUserInfo(user);
			if(user.getName()==null) {
				System.out.println("Enter your name");
				user.setName(scanner.nextLine());
				System.out.println("Enter your address");
				user.setAddress(scanner.nextLine());
				System.out.println("Enter your postal code");
				user.setPostalCode(scanner.nextLine());
				userService.setUserInfo(user);
			}
			Order order = userService.setOrder(user);
			orderService.addOrderToDB(order);
			userService.saveInvoice(user);

			System.out.println("Your Order is ready");
			break;
		}
	}

	private int manageIntegerInputException(String num, Scanner scanner) {
		int choose = 0;
		while (choose==0){
			try {
				choose = Integer.parseInt(num);
				if(choose == 0){
					System.out.println("please enter a valid number");
					num = scanner.nextLine();
				}
			}catch (NumberFormatException e){
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
		switch(choose) {
		case 1:
			System.out.println("Enter food name");
			String foodName = scanner.nextLine();
			Food food = foodService.getFoodWithPriceNameAndRestaurant(foodName, restaurantName);
			userService.removeFoodFromBasket(user, food);
			break;
		case 2:
			System.out.println("Enter food name");
			foodName = scanner.nextLine();
			food = foodService.getFoodWithPriceNameAndRestaurant(foodName, restaurantName);
			System.out.println("Enter new number");
			String num2 = scanner.nextLine();
			int newNumber = manageIntegerInputException(num2, scanner);
			userService.modifyFoodNumberInBasket(user, food, newNumber);
			System.out.println("Your change is applied");
			break;
		}
		showBasket(scanner);
	}

}
