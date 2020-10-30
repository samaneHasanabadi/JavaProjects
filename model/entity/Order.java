package model.entity;
import java.util.HashMap;
import java.util.Map;

public class Order {
	private int orderNumber;
	private String restaurantName;
	private String userName;
	private String userPhoneNumber;
	private Map<Food, Integer> items = new HashMap<>();
	private int wholePrice;

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public Map<Food, Integer> getItems() {
		return items;
	}

	public void setItems(Map<Food, Integer> items) {
		this.items = items;
	}

	public int getWholePrice() {
		return wholePrice;
	}

	public void setWholePrice(int wholePrice) {
		this.wholePrice = wholePrice;
	}
}
