package model.repository;

import model.entity.Food;
import model.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FoodRepository extends CRUDOperation<Food> {

    public void addFood(Food food) {
        creat(food);
    }

    public List<Food> getFoodsOFARestaurant(String restaurantName) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(getReadyQueryWithRestaurantName());
        query.setParameter("restaurantName",restaurantName);
        Restaurant restaurant = (Restaurant) query.uniqueResult();
        transaction.commit();
        session.close();
        return restaurant.getFoods();
    }

    private String getReadyQueryWithRestaurantName() {
        return "from Restaurant restaurant join fetch restaurant.foods" +
                " where restaurant.name = :restaurantName";
    }

    public Food getFoodByNameAndRestaurant(String foodName, String restaurantName) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory()
                .openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select distinct food from Restaurant restaurant join" +
                " restaurant.foods food where restaurant.name =:restaurantName and " +
                "food.name =:foodName");
        query.setParameter("restaurantName",restaurantName);
        query.setParameter("foodName", foodName);
        Food food = (Food) query.uniqueResult();
        transaction.commit();
        session.close();
        return food;
    }

    public String getReadyQueryWithFoodAndRestaurant(){
        return "select Food from Restaurant restaurant join" +
                " fetch restaurant.foods food where restaurant.name = :restaurantName and " +
                "food.name =: foodName";
    }

}
