package model.dto;

import model.entity.Food;

public class OrderDto {
    private Food food;
    private Long sumOfFoodSold;

    public OrderDto(Long sumOfFoodSold, Food food){
        this.food = food;
        this.sumOfFoodSold = sumOfFoodSold;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Long getSumOfFoodSold() {
        return sumOfFoodSold;
    }

    public void setSumOfFoodSold(Long sumOfFoodSold) {
        this.sumOfFoodSold = sumOfFoodSold;
    }
}
