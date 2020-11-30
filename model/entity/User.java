package model.entity;

import javax.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String postalCode;
	private String address;
	private String mobileNumber;
	private int regMonth;
	@OneToOne(cascade = CascadeType.ALL)
	private Basket basket = new Basket();

	public User() {
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

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	@Override
	public String toString() {
		return "name='" + name + '\'' +
				", mobileNumber='" + mobileNumber + '\'';
	}
}
