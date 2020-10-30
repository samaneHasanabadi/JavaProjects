package view;

import model.entity.*;
import service.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserInteraction {
	static RestaurantService restaurantService = new RestaurantService();
	static FoodService foodService = new FoodService();
	static User user;
	static UserService userService = new UserService();
	static String restaurantName;
	static OrderService orderService = new OrderService();
	//static Logger logger = LoggerFactory.getLogger(Main.class);
	public void userWorks (String mobileNumber) {
		//restaurantService.WriteRestaurantsInfoInDB();
		Scanner scanner = new Scanner(System.in);
		user = new User();
		user.setMobileNumber(mobileNumber);
		userService.addUserToDB(user);
		System.out.println("please enter your region");
		int region = scanner.nextInt();
		restaurantService.getRestaurantsInRegionAndPrint(region);
		System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" to see your basket \n4.Enter \"exit\" to exit");
		String temp = scanner.next();
		boolean bool = true;
		while (bool) {
			switch (temp) {
			case "irani":
				restaurantService.getRestaurantsInRegionWithFoodTypeAndPrint(region, "irani");
				temp = returnToPreviouseMenu(scanner, region);
				break;
			case "daryaee":
				restaurantService.getRestaurantsInRegionWithFoodTypeAndPrint(region, "daryaee");
				temp = returnToPreviouseMenu(scanner, region);
				break;
			case "fastfood":
				restaurantService.getRestaurantsInRegionWithFoodTypeAndPrint(region, "fastfood");
				temp = returnToPreviouseMenu(scanner, region);
				break;
			case "beinolmelali":
				restaurantService.getRestaurantsInRegionWithFoodTypeAndPrint(region, "beinolmelali");
				temp = returnToPreviouseMenu(scanner, region);
				break;
			case "basket":
				showBasket(scanner);
				restaurantService.getRestaurantsInRegionAndPrint(region);
				System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" to see your basket\n4.Enter \"exit\" to exit");
				temp = scanner.next();
				break;
			case "exit":
				bool = false;
				break;
			default:
				restaurantName = temp;
				foodService.printFoodsOfARestaurant(temp);
				scanner.nextLine();
				temp = scanner.nextLine();
				switch (temp) {
					case "back":
						restaurantService.getRestaurantsInRegionAndPrint(region);
						System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" to see your basket\n4.Enter \"exit\" to exit");
						temp = scanner.next();
						break;
					case "basket":
						showBasket(scanner);
						restaurantService.getRestaurantsInRegionAndPrint(region);
						System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" to see your basket\n4.Enter \"exit\" to exit");
						temp = scanner.next();
						break;
					default:
						System.out.println("Please enter number of food");
						int number = scanner.nextInt();
						Food food = foodService.getFoodByNameAndRestaurant(temp, restaurantName);
						userService.addFoodToBasket(user, food, number, restaurantName);
						restaurantService.getRestaurantsInRegionAndPrint(region);
						System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" to see your basket\n4.Enter \"exit\" to exit");
						temp = scanner.next();
				}
			}
		}

	}


	public static String returnToPreviouseMenu(Scanner scanner, int region) {
		System.out.println(
				"1.Enter restaurant name \n2.Enter \"back\" to return to previouse menu \n3.Enter \"basket\" to see your basket\"");
		String choose = scanner.next();
		String temp;
		if (choose.equals("back")) {
			restaurantService.getRestaurantsInRegionAndPrint(region);
			System.out.println("1.Enter restaurant name \n2.Enter food type \n3.Enter \"basket\" to see your basket");
			temp = scanner.next();
		} else {
			temp = choose;
		}
		return temp;
	}

	public static void showBasket(Scanner scanner) {
		if (user.getBasket().getItems().isEmpty()) {
			System.out.println("Your basket is empty");
			return;
		}
		for (Food basketItem : user.getBasket().getItems().keySet()) {
			System.out.println(basketItem.getName() + ", price: " + basketItem.getPrice() + ", number: " + user.getBasket().getItems().get(basketItem));
		}
		System.out.println("Whole Price: " + user.getBasketPrice());
		System.out.println("1.modify basket \n2.back to perviouse menu\n3.Finale your order");
		int choose = scanner.nextInt();
		switch(choose) {
		case 1:
			modifyBasket(user, scanner);
		case 2:
			return;
		case 3:
			user = userService.getUserInfo(user);
			if(user.getName()==null) {
				System.out.println("Enter your name");
				scanner.nextLine();
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
			System.out.println("Your model.entity.Order is ready");
			break;
		}
	}
	
	public static void modifyBasket(User user, Scanner scanner) {
		System.out.println("1.Remove a food \n2.Modify food number");
		int choose = scanner.nextInt();
		switch(choose) {
		case 1:
			System.out.println("Enter food name");
			scanner.nextLine();
			String foodName = scanner.nextLine();
			Food food = foodService.getFoodWithPriceNameAndRestaurant(foodName, restaurantName);
			userService.removeFoodFromBasket(user, food);
			break;
		case 2:
			System.out.println("Enter food name");
			scanner.nextLine();
			foodName = scanner.nextLine();
			food = foodService.getFoodWithPriceNameAndRestaurant(foodName, restaurantName);
			System.out.println("Enter new number");
			int newNumber = scanner.nextInt();
			userService.modifyFoodNumberInBasket(user, food, newNumber);
			System.out.println("Your change is applied");
			break;
		}
		showBasket(scanner);
	}

}
