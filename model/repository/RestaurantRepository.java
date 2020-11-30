package model.repository;

import model.entity.FoodType;
import model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query("select distinct restaurant from Restaurant restaurant join fetch restaurant.foods where " +
            "restaurant.name = :restaurantName")
    Restaurant findRestaurantByName(@Param("restaurantName") String restaurantName);
    List<Restaurant> findByRegionAndAndFoodTypes(int region, FoodType foodType);
    List<Restaurant> findByRegion(int region);
}
