public class Listing {
    private String id;
    private double cost;
    private double area;
    private String url;

    public Listing(String id, double cost, double area, String url) {
        this.id = id;
        this.cost = cost;
        this.area = area;
        this.url = url;
    }

    public String getId() {return id;}
    public double getCost() {return cost;}
    public double getArea() {return area;}
    public String getUrl() {return url;}

    public void setId(String id) {this.id = id;}
    public void setCost(double cost) {this.cost = cost;}
    public void setArea(double area) {this.area = area;}
    public void setUrl(String url) {this.url = url;}
}

