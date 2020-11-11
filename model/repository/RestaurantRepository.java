package model.repository;

import model.entity.Food;
import model.entity.FoodType;
import model.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.UserInteraction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRepository extends CRUDOperation<Restaurant> {
    Logger logger = LoggerFactory.getLogger(UserInteraction.class);

    public List<Restaurant> getRestaurantsInARegion(int region) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query query = session.createQuery("select distinct restaurant from Restaurant " +
                "restaurant join fetch restaurant.foodTypes where restaurant.region =:region",
                Restaurant.class);
        query.setParameter("region", region);
        List<Restaurant> restaurants = query.list();
        return restaurants;
    }

    public List<Restaurant> getRestaurantsInARegionWithType(int region, String foodType) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query<Restaurant> query = session.createQuery("select distinct restaurant from " +
                "Restaurant restaurant join fetch restaurant.foodTypes join" +
                " restaurant.foods food where restaurant.region =: r and food.type =: type"
                , Restaurant.class);
        query.setParameter("r", region);
        query.setParameter("type", FoodType.valueOf(foodType));
        List<Restaurant> restaurants = query.list();
        session.close();
        return restaurants;
    }

    public Restaurant getRestaurantByName(String restaurantName) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        logger.trace("session opened");
        Query query = session.createQuery("from Restaurant restaurant " +
                "where restaurant.name = :n");
        query.setParameter("n", restaurantName);
        List<Restaurant> restaurants = (List<Restaurant>) query.list();
        session.close();
        logger.trace("session closed");
        return restaurants.get(0);
    }

    public List<Restaurant> selectAll(){
        List<Restaurant> restaurants = selectAll();
        return restaurants;
    }
}
