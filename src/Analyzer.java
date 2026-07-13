public class Analyzer {
    public void analyzeDeal(Listing target){
        double pricePerSquare = target.getCost() / target.getArea();
        System.out.println("Price per square: " + pricePerSquare);

    }
}
