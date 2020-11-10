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
	private String restaurantName;
	@OneToOne
	private User user;

	public Basket() {
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

	public Map<Food, Integer> getItems() {
		return items;
	}

	public void setItems(Map<Food, Integer> items) {
		this.items = items;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
}
