package configuration;

import model.dto.BasketDto;
import model.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.*;

@Configuration
@ComponentScan(basePackages = "model.repository")
public class SpringContext {

    @Bean
    public BasketService basketService(){
        return new BasketService();
    }

    @Bean
    public FoodService foodService(FoodRepository foodRepository, RestaurantRepository restaurantRepository){
        return new FoodService(foodRepository, restaurantRepository);
    }

    @Bean
    public ManagerService managerService(ManagerRepository managerRepository){
        return new ManagerService(managerRepository);
    }

    @Bean
    public RestaurantService restaurantService(RestaurantRepository restaurantRepository, FoodService foodService){
        return new RestaurantService(restaurantRepository, foodService);
    }

    @Bean
    public UserService userService(UserRepository userRepository, BasketRepository basketRepository, BasketService basketService){
        return  new UserService(userRepository, basketRepository, basketService);
    }
}
