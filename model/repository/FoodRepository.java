package model.repository;

import model.entity.Food;
import model.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FoodRepository extends CRUDOperation<Food> {

    Logger logger = LoggerFactory.getLogger(UserInteraction.class);

    public void addFood(Food food) {
        creat(food);
    }

    public List<Food> getFoodsOFARestaurant(String restaurantName) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        logger.trace("session opened");
        Query query = session.createQuery("from Restaurant restaurant " +
                "where restaurant.name = :n");
        query.setParameter("n",restaurantName);
        List<Restaurant> restaurants = (List<Restaurant>) query.list();
        List<Food> foods = new ArrayList<>();
        if(restaurants != null && !restaurants.isEmpty())
            foods = restaurants.get(0).getFoods();
        session.close();
        logger.trace("session closed");
        return foods;
    }

    public Food getFoodByNameAndRestaurant(String foodName, String restaurantName) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        logger.trace("session opened");
        Query query = session.createQuery("from Restaurant restaurant" +
                " where restaurant.name = :n", Restaurant.class);
        query.setParameter("n",restaurantName);
        List<Restaurant> restaurants = (List<Restaurant>) query.list();
        session.close();
        logger.trace("session closed");
        return restaurants.get(0).getFoods().stream().filter(food -> food.getName().equals(foodName))
                .collect(Collectors.toList()).get(0);
    }

}
