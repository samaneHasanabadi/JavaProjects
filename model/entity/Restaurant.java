package model.entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurant {
	private String name;
	private int region;
	private int shipmentPrice;
	private int ordersNumber;
	private ArrayList<FoodType> foodTypes = new ArrayList<FoodType>();
	private ArrayList<Food> foods = new ArrayList<Food>();
	private Map<Food,Integer> foodAmountSold = new HashMap<>();

	public Restaurant(){

	}

	public Restaurant(String name, int region, int shipmentPrice) {
		this.name = name;
		this.region = region;
		this.shipmentPrice = shipmentPrice;
	}

	public Map<Food, Integer> getFoodAmountSold() {
		return foodAmountSold;
	}

	public void setFoodAmountSold(Map<Food, Integer> foodAmountSold) {
		this.foodAmountSold = foodAmountSold;
	}

	public int getOrdersNumber() {
		return ordersNumber;
	}

	public void setOrdersNumber(int ordersNumber) {
		this.ordersNumber = ordersNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRegion() {
		return region;
	}
	
	public void setRegion(int region) {
		this.region = region;
	}
	
	public int getShipmentPrice() {
		return shipmentPrice;
	}
	
	public void setShipmentPrice(int shipmentPrice) {
		this.shipmentPrice = shipmentPrice;
	}
	
	public ArrayList<FoodType> getFoodTypes() {
		return foodTypes;
	}
	
	public ArrayList<Food> getFoods() {
		return foods;
	}

}
