package model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
public class OrderClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int orderNumber;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderClass")
	private List<FoodCountMapping> items = new ArrayList<>();
	@ManyToOne()
	private User user;
	@ManyToOne()
	private Restaurant restaurant;
	private int wholePrice;

	public OrderClass(){

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<FoodCountMapping> getItems() {
		return items;
	}

	public void setItems(List<FoodCountMapping> items) {
		this.items = items;
	}

	public int getWholePrice() {
		return wholePrice;
	}

	public void setWholePrice(int wholePrice) {
		this.wholePrice = wholePrice;
	}
}
