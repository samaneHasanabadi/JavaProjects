package model.repository;

import model.entity.FoodType;
import model.entity.Restaurant;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

import java.util.List;

public class RestaurantRepository extends CRUDOperation<Restaurant> {


    public List<Restaurant> getRestaurantByRegionAndFoodType(Integer region, String foodType){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Restaurant.class);
        if(region != null) {
            addRegionRestriction(region, criteria);
        }
        if(foodType != null) {
            addFoodTypeRestriction(foodType, criteria);
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Restaurant> restaurants = criteria.list();
        transaction.commit();
        session.close();
        return restaurants;
    }

    private void addRegionRestriction(Integer region, Criteria criteria) {
        criteria.add(Restrictions.eq("region", region));
    }

    private void addFoodTypeRestriction(String foodType, Criteria criteria) {
        criteria.createAlias("foodTypes", "foodTypes");
        criteria.add(Restrictions.in("foodTypes", FoodType.valueOf(foodType)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    public Restaurant getRestaurantByName(String restaurantName) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(prepareQueryByRestaurantName());
        setRestaurantNameParameter(restaurantName, query);
        Restaurant restaurant = (Restaurant) query.uniqueResult();
        transaction.commit();
        session.close();
        return restaurant;
    }

    private void setRestaurantNameParameter(String restaurantName, Query query) {
        query.setParameter("restaurantName", restaurantName);
    }

    private String prepareQueryByRestaurantName() {
        return "select distinct restaurant from Restaurant restaurant join fetch restaurant.foods where " +
                "restaurant.name = :restaurantName";
    }

    @Override
    public List<Restaurant> selectAll() {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query query = session.createQuery("select restaurant from Restaurant restaurant", Restaurant.class);
        List<Restaurant> list = query.list();
        return list;
    }
}
