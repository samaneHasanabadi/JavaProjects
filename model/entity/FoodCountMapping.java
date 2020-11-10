package model.entity;

import javax.persistence.*;

@Entity
public class FoodCountMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Food food;
    private int number;
    @ManyToOne
    private OrderClass orderClass;

    public FoodCountMapping(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int count) {
        this.number = count;
    }

    public OrderClass getOrderClass() {
        return orderClass;
    }

    public void setOrderClass(OrderClass orderClass) {
        this.orderClass = orderClass;
    }
}
