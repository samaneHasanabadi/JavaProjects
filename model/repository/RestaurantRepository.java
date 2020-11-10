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
        Query query = session.createQuery("from Restaurant restaurant where restaurant.region =:region",
                Restaurant.class);
        query.setParameter("region", region);
        List<Restaurant> restaurants = query.list();
        restaurants.forEach(restaurant -> restaurant.getFoodTypes().forEach(foodType ->
                System.out.println(foodType)));
        session.close();
        return restaurants;
    }

    public List<Restaurant> getRestaurantsInARegionWithType(int region, String foodType) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query<Restaurant> query = session.createQuery("from Restaurant restaurant " +
                " where restaurant.region =: r", Restaurant.class);
        query.setParameter("r", region);
        List<Restaurant> restaurants = query.list();
        List<Restaurant> restaurantList = restaurants.stream().filter(restaurant ->
                restaurant.getFoodTypes().stream().anyMatch(foodType1 -> foodType1.
                        equals(FoodType.valueOf(foodType)))).collect(Collectors.toList());
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
