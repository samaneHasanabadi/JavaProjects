package service;

import exceptions.MoreThanOneRestaurantInBasketException;
import exceptions.NoSuchFoodInBasketException;
import model.entity.*;
import model.repository.BasketRepository;
import model.repository.UserRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    private BasketRepository basketRepository = new BasketRepository();
    private BasketService basketService = new BasketService();
    public User getUserInfo(User user){
        return userRepository.getUserInfo(user);
    }

    public void setUserInfo(User user){
        userRepository.SetUserInfo(user);
    }

    public User addUserToDB(User user){
        User dbUser = userRepository.getUserByMobileNumberAndBasket(user.getMobileNumber());
        if(dbUser == null){
            dbUser = userRepository.getUserByMobileNumber(user.getMobileNumber());
            if(dbUser == null) {
                userRepository.addUser(user);
            }else {
                user = dbUser;
            }
        }else {
            user = dbUser;
        }
        return user;
    }

    public void addFoodToBasket(User user, Food food, int number, Restaurant restaurant) throws Exception {
        if (user.getBasket().getItems().isEmpty() || restaurant.getName().equals(user.getBasket()
                .getRestaurant().getName())) {
            basketService.addFood(user.getBasket(),food, number);
            user.getBasket().setRestaurant(restaurant);
            basketRepository.update(user.getBasket());
        }else {
            throw new MoreThanOneRestaurantInBasketException();
        }
    }

    public void removeFoodFromBasket(User user,Food food) throws NoSuchFoodInBasketException {
        basketService.removeFood(user.getBasket(), food);
        basketRepository.update(user.getBasket());
    }

    public void modifyFoodNumberInBasket(User user,Food food, int newNumber) throws Exception {
        basketService.modifyFoodNumber(user.getBasket(), food, newNumber);
        basketRepository.update(user.getBasket());
    }

    public void setOrder(User user) {
        user.getBasket().setInvoiced(true);
        basketRepository.creat(user.getBasket());
    }

    public void saveInvoice(User user) {
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            BufferedWriter fileWiter = new BufferedWriter(new FileWriter("//home//samane//" +
                    "Homwork7-OnlineFoodOrder//"+user.getName()+ "_"+ ts.toString() +".txt"));
            fileWiter.write("Order Number: " + user.getBasket().getId());
            fileWiter.newLine();
            fileWiter.write("User Name: " + user.getName());
            fileWiter.newLine();
            fileWiter.write("User Address: " + user.getAddress());
            fileWiter.newLine();
            fileWiter.write("User Postal Code: " + user.getPostalCode());
            fileWiter.newLine();
            fileWiter.write("User Mobile Number: " + user.getMobileNumber());
            fileWiter.newLine();
            fileWiter.write("Restaurant Name: " + user.getBasket().getRestaurant().getName());
            fileWiter.newLine();
            fileWiter.write("Food Name\t\tPrice\t\tNumber");
            fileWiter.newLine();
            Set<Food> foods = user.getBasket().getItems().keySet();
            for (Food food : foods) {
                fileWiter.write(food.getName() + "\t\t" + food.getPrice() + "\t\t" +
                        user.getBasket().getItems().get(food));
                fileWiter.newLine();
            }
            fileWiter.write("Whole Price: " + user.getBasket().getWholePrice());
            fileWiter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearBasket(User user){
        user.setBasket(new Basket());
        basketRepository.creat(user.getBasket());
        userRepository.update(user);
    }

}
