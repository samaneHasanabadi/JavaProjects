package model.repository;

import model.dto.OrderDto;
import model.entity.Manager;
import model.entity.OrderClass;
import model.entity.Restaurant;
import model.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerRepository extends CRUDOperation<Manager> {

    public List<User> getUsersWithMonthRegisterationAndSumOfOrderPrice(){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query query = session.createQuery("select sum(o.wholePrice), u from " +
                "OrderClass o join o.user u group by o.user");
        List<Object[]> list = query.list();
        List<User> users = new ArrayList<>();
        list.stream().forEach(o -> {
            ((User) o[1]).setOrdersSumPrice(((Long) o[0]).intValue());
            users.add((User) o[1]);
        });
        return users;
    }

    public Map<Restaurant, List<OrderDto>> getRestaurantsWithPeykIncomeAndFoodSold(){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query query = session.createQuery("select distinct restaurant from Restaurant restaurant" +
                " join fetch restaurant.orders");
        List<Restaurant> list = query.list();
        Map<Restaurant, List<OrderDto>> restaurantFoodSoldMap = new HashMap<>();
        list.forEach(a -> {
            Query query1 = session.createQuery("select i.food as food, sum(i.number) as" +
                    " numberSold from OrderClass o join o.items i where o.restaurant.id =:id " +
                        "group by i.food");
            query1.setParameter("id", a.getId());
            List<OrderDto> list2 = query1.setResultTransformer(
            Transformers.aliasToBean(OrderDto.class)).list();
            restaurantFoodSoldMap.put(a, list2);
        });
        /*Query query1 = session.createQuery("select sum(VALUE(items)), key(items) from Basket basket join" +
                " basket.items items where key(items).name=: foodName group by key(items) ");
        query1.setParameter("foodName","Morghe sookhari");
        List list1 = query1.list();*/
        session.close();
        return restaurantFoodSoldMap;
    }

}
