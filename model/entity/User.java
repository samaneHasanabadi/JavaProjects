package model.entity;

import model.entity.Basket;
import model.entity.Food;
import model.entity.Order;

import java.util.ArrayList;

public class User {
	private String name;
	private String postalCode;
	private String address;
	private String mobileNumber;
	private int ordersSumPrice;
	private int regMonth;
	private int basketPrice;
	private Basket basket = new Basket();
	private ArrayList<Order> orders = new ArrayList<Order>();

	public String getName() {
		return name;
	}
	public int getRegMonth() {
		return regMonth;
	}

	public void setRegMonth(int regMonth) {
		this.regMonth = regMonth;
	}

	public int getOrdersSumPrice() {
		return ordersSumPrice;
	}

	public void setOrdersSumPrice(int ordersSumPrice) {
		this.ordersSumPrice = ordersSumPrice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getBasketPrice() {
		return basketPrice;
	}

	public void setBasketPrice(int basketPrice) {
		this.basketPrice = basketPrice;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "name='" + name + '\'' +
				", mobileNumber='" + mobileNumber + '\'';
	}
}
