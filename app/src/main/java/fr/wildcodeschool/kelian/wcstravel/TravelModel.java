package fr.wildcodeschool.kelian.wcstravel;

public class TravelModel {

    private String departure;
    private String destination;
    private Double price;
    private String company;

    public TravelModel(String departure, String destination, Double price, String company) {
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.company = company;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}