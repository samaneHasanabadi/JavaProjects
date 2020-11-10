package model.dto;

import model.entity.Food;

public class OrderDto {
    private Food food;
    private Long numberSold;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Long getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(Long numberSold) {
        this.numberSold = numberSold;
    }


}
