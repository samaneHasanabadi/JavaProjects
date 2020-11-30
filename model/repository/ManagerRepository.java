package model.repository;

import model.dto.BasketDto;
import model.dto.OrderDto;
import model.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @Query("select sum(user.basket.wholePrice) as sumOfOrdersPrice, user as user from " +
            "User user where user.basket.isInvoiced=true " +
            "group by user.id")
    List<BasketDto> getUsersWithSumOfOrdersPrice();

    @Query("select count(basket) from Basket " +
            "basket where basket.restaurant.id =:restaurantId")
    int getNumberOfOrdersOfRestaurant(@Param("restaurantId") int restaurantId);

    @Query("select new model.dto.OrderDto(sum(VALUE(items)), key(items)) from" +
            " Basket basket join basket.items items where basket.isInvoiced= true and " +
            "basket.restaurant.id = :restaurantId group by key(items).id")
    List<OrderDto> getSumOfFoodOrderForRestaurant(@Param("restaurantId") int restaurantId);

}
