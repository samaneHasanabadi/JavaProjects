package model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String postalCode;
	private String address;
	private String mobileNumber;
	private int ordersSumPrice;
	private int regMonth;
	private int basketPrice;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Basket basket = new Basket();
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<OrderClass> orders = new ArrayList<>();

	public User() {
	}

	public void setOrders(List<OrderClass> orders) {
		this.orders = orders;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public List<OrderClass> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<OrderClass> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "name='" + name + '\'' +
				", mobileNumber='" + mobileNumber + '\'';
	}
}
