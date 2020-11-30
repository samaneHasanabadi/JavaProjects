package model.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
@Entity
public class Basket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "BasketItemMapping",
			joinColumns = {@JoinColumn(name = "BasketId", referencedColumnName = "id")})
	@MapKeyColumn(name = "food_name")
	@Column(name = "number")
	private Map<Food, Integer> items = new HashMap<>();
	@ManyToOne
	private Restaurant restaurant;
	private boolean isInvoiced = false;
	private int wholePrice;

	public Basket() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Food, Integer> getItems() {
		return items;
	}

	public void setItems(Map<Food, Integer> items) {
		this.items = items;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public boolean isInvoiced() {
		return isInvoiced;
	}

	public void setInvoiced(boolean invoiced) {
		isInvoiced = invoiced;
	}

	public int getWholePrice() {
		return wholePrice;
	}

	public void setWholePrice(int wholePrice) {
		this.wholePrice = wholePrice;
	}
}
