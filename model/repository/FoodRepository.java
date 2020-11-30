package model.repository;

import model.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query("select distinct food from Restaurant restaurant join" +
            " restaurant.foods food where restaurant.name =:restaurantName and " +
            "food.name =:foodName")
    public Food getFoodByNameAndRestaurant(@Param("foodName") String foodName, @Param("restaurantName") String restaurantName);
}
