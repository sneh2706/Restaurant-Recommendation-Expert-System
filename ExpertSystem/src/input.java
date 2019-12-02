public class input {
    private String name, address, city, type, Cuisine, price, rating;

    public input(String name, String address, String city, String type, String cusiene, String price, String rating) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.type = type;
        this.Cuisine = cusiene;
        this.price = price;
        this.rating = rating;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cusiene) {
        this.Cuisine = cusiene;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
