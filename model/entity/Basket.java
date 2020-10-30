package model.entity;

import java.util.HashMap;
import java.util.Map;

public class Basket {
	private Map<Food, Integer> items = new HashMap<Food, Integer>();
	private String resturant;

	public Map<Food, Integer> getItems() {
		return items;
	}

	public void setItems(Map<Food, Integer> items) {
		this.items = items;
	}

	public String getResturant() {
		return resturant;
	}

	public void setResturant(String resturant) {
		this.resturant = resturant;
	}
}
