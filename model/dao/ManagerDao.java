package model.dao;

import model.entity.Food;
import model.entity.Restaurant;
import model.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManagerDao {

    public ArrayList<User> getUsersWithMonthRegisterationAndSumOfOrderPrice(Connection connection){
        ArrayList<User> users = null;
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT tb1.*, sum(price),extract(month from" +
                    " tb1.regTime) FROM users as tb1 join order_has_food on" +
                    " mobilePhone=order_has_food.orders_users_mobilePhone1 join" +
                    " restaurant_has_foods on order_has_food.foods_name = " +
                    "restaurant_has_foods.foods_name and order_has_food.orders_restaurants_name " +
                    "=restaurant_has_foods.restaurants_name group by mobilePhone");
            users = getUsersOfResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public String getSqlQueryReady(int month, int minSum, int maxSum){
        return  "SELECT tb1.*, sum(price) FROM users as tb1 join order_has_food on " +
                "mobilePhone=order_has_food.orders_users_mobilePhone1 join restaurant_has_foods on" +
                " order_has_food.foods_name=restaurant_has_foods.foods_name and " +
                "order_has_food.orders_restaurants_name=restaurant_has_foods.restaurants_name " +
                "where extract(month from tb1.regTime) = "+month+" group by mobilePhone having " +
                "sum(price) < "+maxSum+" and sum(price) > " + minSum;
    }

    public ArrayList<User> getUsersOfResultSet(ResultSet rs) {
        ArrayList<User> users = new ArrayList<User>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setMobileNumber(rs.getString(1));
                user.setName(rs.getString(2));
                user.setRegMonth(rs.getInt(7));
                user.setOrdersSumPrice(rs.getInt(6));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public List<Restaurant> getRestaurantsWithPeykIncomeAndFoodSold(Connection connection){
        List<Restaurant> restaurants = null;
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT tb1.*, count(*) FROM restaurants as tb1 join" +
                    " orders on name=restaurants_name group by name");
            restaurants = setFoodSoldInRestaurant(getFoodCuntSoldOfARestaurant(connection), preparedRestaurantsOfResultSet(rs));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return restaurants;
    }

    public List<Restaurant> preparedRestaurantsOfResultSet(ResultSet rs){
        List<Restaurant> restaurants = new ArrayList<>();
        try{
            while (rs.next()){
                Restaurant restaurant = new Restaurant(rs.getString(1),
                        rs.getInt(2), rs.getInt(3));
                restaurant.setOrdersNumber(rs.getInt(4));
                restaurants.add(restaurant);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return restaurants;
    }

    public ResultSet getFoodCuntSoldOfARestaurant(Connection connection){
        ResultSet rs= null ;
        try {
            Statement stm = connection.createStatement();
            rs = stm.executeQuery("SELECT foods_name, orders_restaurants_name, sum(numbers) FROM" +
                    " OnlineFoodOrder.order_has_food group by foods_name , orders_restaurants_name");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public List<Restaurant> setFoodSoldInRestaurant(ResultSet rs, List<Restaurant> restaurants){
        try{
            while(rs.next()){
                for(Restaurant restaurant: restaurants){
                    if(restaurant.getName().equals(rs.getString(2))){
                        Food food = new Food();
                        food.setName(rs.getString(1));
                        restaurant.getFoodAmountSold().put(food,rs.getInt(3));
                    }
                }
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return restaurants;
    }
}
