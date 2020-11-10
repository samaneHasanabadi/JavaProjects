package model.dto;

public class UserDto {
    private String name;
    private String address;
    private String postalcod;

    public UserDto(){

    }

    public UserDto(String name, String address, String postalcod) {
        this.name = name;
        this.address = address;
        this.postalcod = postalcod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcod() {
        return postalcod;
    }

    public void setPostalcod(String postalcod) {
        this.postalcod = postalcod;
    }
}
