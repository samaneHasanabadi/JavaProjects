package model.entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int region;
	private int deliveryAmount;
	@ElementCollection(targetClass = FoodType.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<FoodType> foodTypes = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL)
	private List<Food> foods = new ArrayList<>();
	private int numberOfOrders;

	public Restaurant(){

	}

	public Restaurant(String name, int region, int shipmentPrice) {
		this.name = name;
		this.region = region;
		this.deliveryAmount = shipmentPrice;
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
	
	public int getDeliveryAmount() {
		return deliveryAmount;
	}
	
	public void setDeliveryAmount(int shipmentPrice) {
		this.deliveryAmount = shipmentPrice;
	}
	
	public List<FoodType> getFoodTypes() {
		return foodTypes;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public int getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(int numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}

	public int getDeliveryIncome(){
		return numberOfOrders * deliveryAmount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Restaurant that = (Restaurant) o;
		return  region == that.region &&
				deliveryAmount == that.deliveryAmount &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, region, deliveryAmount, foodTypes, foods, numberOfOrders);
	}
}
