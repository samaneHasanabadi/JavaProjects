package service;

import exceptions.MoreThanOneRestaurantInBasketException;
import exceptions.NoSuchFoodInBasketException;
import model.entity.*;
import model.repository.BasketRepository;
import model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private BasketRepository basketRepository;
    private BasketService basketService;

    @Autowired
    public UserService(UserRepository userRepository, BasketRepository basketRepository, BasketService basketService){
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
        this.basketService = basketService;
    }

    public User getUserInfo(User user){
        User dbUser = userRepository.getById(user.getId());
        if(dbUser != null){
            user.setName(dbUser.getName());
            user.setPostalCode(dbUser.getPostalCode());
            user.setAddress(dbUser.getAddress());
        }
        return user;
    }

    public void setUserInfo(User user){
        userRepository.save(user);
    }

    public User addUserToDB(User user){
        User dbUser = userRepository.findByMobileNumberAndBasket_IsInvoiced(user.getMobileNumber(), false);
        if(dbUser == null){
            dbUser = userRepository.findByMobileNumber(user.getMobileNumber());
            if(dbUser == null) {
                userRepository.save(user);
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
            basketRepository.save(user.getBasket());
        }else {
            throw new MoreThanOneRestaurantInBasketException();
        }
    }

    public void removeFoodFromBasket(User user,Food food) throws NoSuchFoodInBasketException {
        basketService.removeFood(user.getBasket(), food);
        basketRepository.save(user.getBasket());
    }

    public void modifyFoodNumberInBasket(User user,Food food, int newNumber) throws Exception {
        basketService.modifyFoodNumber(user.getBasket(), food, newNumber);
        basketRepository.save(user.getBasket());
    }

    public void setOrder(User user) {
        user.getBasket().setInvoiced(true);
        basketRepository.save(user.getBasket());
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
        basketRepository.save(user.getBasket());
        userRepository.save(user);
    }

}
