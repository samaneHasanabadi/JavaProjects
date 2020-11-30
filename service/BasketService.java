package service;

import exceptions.NegativeFoodNumberException;
import exceptions.NoSuchFoodInBasketException;
import model.entity.Basket;
import model.entity.Food;

public class BasketService {

    public void modifyBasketPrice(Basket basket, int amount){
        basket.setWholePrice(basket.getWholePrice() + amount);
    }

    public void removeFood(Basket basket, Food food) throws NoSuchFoodInBasketException {
        if(food == null){
            throw new NullPointerException("food can not be null");
        }
        if(basket.getItems().containsKey(food)){
            modifyBasketPrice(basket, - food.getPrice()*basket.getItems().get(food));
            basket.getItems().remove(food);
        }else {
            throw new NoSuchFoodInBasketException();
        }
    }

    public void addFood(Basket basket, Food food, int number) throws NegativeFoodNumberException {
        if(number < 0){
            throw new NegativeFoodNumberException();
        }
        if(food == null){
            throw new NullPointerException("food can not be null");
        }
        basket.getItems().put(food, number);
        modifyBasketPrice(basket, food.getPrice() * number);
    }

    public void modifyFoodNumber(Basket basket, Food food, int newNumber) throws Exception {
        removeFood(basket, food);
        addFood(basket, food, newNumber);
    }

}
