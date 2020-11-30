package model.repository;

import model.dto.BasketDto;
import model.dto.OrderDto;
import model.entity.Manager;
import model.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerRepository extends CRUDOperation<Manager> {

    public List<BasketDto> getUsersWithSumOfOrdersPrice(){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(getQueryForUserSumOrdersPrice());
        List<BasketDto> orders = query.setResultTransformer(
                Transformers.aliasToBean(BasketDto.class)).list();
        transaction.commit();
        session.close();
        return orders;
    }

    private String getQueryForUserSumOrdersPrice() {
        return "select sum(user.basket.wholePrice) as sumOfOrdersPrice, user as user from " +
                "User user where user.basket.isInvoiced=true " +
                "group by user.id";
    }

    public Map<Restaurant,List<OrderDto>> getSumOfFoodSoldInEachRestaurant(){
        HashMap<Restaurant, List<OrderDto>> restaurantsMapFood = new HashMap<>();
        List<Restaurant> restaurants = new RestaurantRepository().selectAll();
        restaurants.stream().forEach(restaurant -> {
            restaurant.setNumberOfOrders(getNumberOfOrdersOfRestaurant(restaurant.getId()));
            List<OrderDto> orders = getSumOfFoodOrderForRestaurant(restaurant.getId());
            restaurantsMapFood.put(restaurant, orders);
        });
        return restaurantsMapFood;
    }

    private int getNumberOfOrdersOfRestaurant(int restaurantId) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(getQueryForNumberOfOrders());
        query.setParameter("restaurantId", restaurantId);
        int numberOfOrders = ((Long) query.uniqueResult()).intValue();
        transaction.commit();
        session.close();
        return numberOfOrders;
    }

    private String getQueryForNumberOfOrders() {
        return "select count(*) from Basket " +
                "basket where basket.restaurant.id =:restaurantId";
    }

    private List<OrderDto> getSumOfFoodOrderForRestaurant(int restaurantId) {
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(getQueryForSumOfFood());
        query.setParameter("restaurantId", 4);
        query.setResultTransformer(Transformers.aliasToBean(OrderDto.class));
        List<OrderDto> orders = query.list();
        System.out.println(orders.get(0).getFood().getName());
        transaction.commit();
        session.close();
        return orders;
    }

    private String getQueryForSumOfFood() {
        return "select sum(VALUE(items)) as sumOfFoodSold, key(items) as food from" +
                " Basket basket join basket.items items where basket.isInvoiced= true and " +
                "basket.restaurant.id = :restaurantId group by key(items).id ";
    }

}
