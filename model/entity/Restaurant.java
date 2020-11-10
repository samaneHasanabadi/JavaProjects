package model.entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int region;
	private int shipmentPrice;
	@OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
	private List<OrderClass> orders = new ArrayList<>();
	@ElementCollection(targetClass = FoodType.class)
	@Enumerated(EnumType.STRING)
	private List<FoodType> foodTypes = new ArrayList<>();
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Food> foods = new ArrayList<>();

	public Restaurant(){

	}

	public Restaurant(String name, int region, int shipmentPrice) {
		this.name = name;
		this.region = region;
		this.shipmentPrice = shipmentPrice;
	}

	public void setFoodTypes(List<FoodType> foodTypes) {
		this.foodTypes = foodTypes;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFoodTypes(ArrayList<FoodType> foodTypes) {
		this.foodTypes = foodTypes;
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
	
	public List<FoodType> getFoodTypes() {
		return foodTypes;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public List<OrderClass> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderClass> orders) {
		this.orders = orders;
	}
}
