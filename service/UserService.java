package service;

import model.Repository.UserRepository;
import model.entity.Basket;
import model.entity.Food;
import model.entity.Order;
import model.entity.User;
import model.Repository.ConnectionRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Set;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    private Connection connection = ConnectionRepository.connectionRepository.getConnection();

    public User getUserInfo(User user){
        return userRepository.getUserInfo(connection, user);
    }

    public void setUserInfo(User user){
        userRepository.SetUserInfo(connection, user);
    }

    public void addUserToDB(User user){
        userRepository.addUser(connection, user);
    }

    public boolean addFoodToBasket(User user, Food food, int number, String restaurant) {
        boolean restaurentCheck = false;
        if (user.getBasket().getItems().isEmpty() || restaurant.equals(user.getBasket()
                .getRestaurantName())) {
            user.getBasket().getItems().put(food, number);
            user.setBasketPrice(user.getBasketPrice() + food.getPrice() * number);
            user.getBasket().setRestaurantName(restaurant);
            restaurentCheck = true;
        }
        return restaurentCheck;
    }

    public boolean removeFoodFromBasket(User user,Food food) {
        boolean success = false;
        try {
            user.setBasketPrice(user.getBasketPrice() - food.getPrice() * user.getBasket().
                    getItems().get(food));
            user.getBasket().getItems().remove(food);
            success = true;
        } catch (NullPointerException e) {
            success = false;
        }
        return success;
    }

    public boolean modifyFoodNumberInBasket(User user,Food food, int newNumber) {
        boolean foodExistence = false;
        try {
            user.setBasketPrice(user.getBasketPrice() - food.getPrice() * user.getBasket()
                    .getItems().get(food));
            user.getBasket().getItems().put(food, newNumber);
            user.setBasketPrice(user.getBasketPrice() + food.getPrice() * newNumber);
            foodExistence = true;
        } catch (NullPointerException e) {
            foodExistence = false;
        }
        return foodExistence;
    }

    public Order setOrder(User user) {
        Order order = new Order();
        order.setOrderNumber(user.getOrders().size() + 2);
        order.setItems(user.getBasket().getItems());
        order.setRestaurantName(user.getBasket().getRestaurantName());
        order.setUserName(user.getName());
        order.setUserPhoneNumber(user.getMobileNumber());
        order.setWholePrice(user.getBasketPrice());
        user.getOrders().add(order);
        user.setOrdersSumPrice(user.getBasketPrice());
        return order;
    }

    public void saveInvoice(User user) {
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            BufferedWriter fileWiter = new BufferedWriter(new FileWriter("//home//samane//" +
                    "Homwork7-OnlineFoodOrder//"+user.getName()+ "_"+ ts.toString() +".txt"));
            fileWiter.write("model.entity.Order Number: " + user.getOrders().get(user.getOrders()
                    .size() - 1).getOrderNumber());
            fileWiter.newLine();
            fileWiter.write("User Name: " + user.getName());
            fileWiter.newLine();
            fileWiter.write("User Address: " + user.getAddress());
            fileWiter.newLine();
            fileWiter.write("User Postal Code: " + user.getPostalCode());
            fileWiter.newLine();
            fileWiter.write("User Mobile Number: " + user.getMobileNumber());
            fileWiter.newLine();
            fileWiter.write("Restaurant Name: " + user.getBasket().getRestaurantName());
            fileWiter.newLine();
            fileWiter.write("Food Name\t\tPrice\t\tNumber");
            fileWiter.newLine();
            Set<Food> foods = user.getBasket().getItems().keySet();
            for (Food food : foods) {
                fileWiter.write(food.getName() + "\t\t" + food.getPrice() + "\t\t" +
                        user.getBasket().getItems().get(food));
                fileWiter.newLine();
            }
            fileWiter.write("Whole Price: " + user.getBasketPrice());
            fileWiter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearBasket(User user){
        user.setBasket(new Basket());
        user.setBasketPrice(0);
    }
}
