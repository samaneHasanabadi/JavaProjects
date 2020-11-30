package model.dto;

import model.entity.User;

public class BasketDto {
    private Long sumOfOrdersPrice;
    private User user;

    public Long getSumOfOrdersPrice() {
        return sumOfOrdersPrice;
    }

    public void setSumOfOrdersPrice(Long sumOfOrdersPrice) {
        this.sumOfOrdersPrice = sumOfOrdersPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
