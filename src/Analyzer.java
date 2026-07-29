public class Analyzer {
    public void analyzeDeal(Listing target) {
        double pricePerSquare = target.getCost() / target.getArea();

        System.out.println("Analyzing URL: " + target.getUrl());
        System.out.println("Price per SQM: " + pricePerSquare + " KZT");

        if (pricePerSquare < 480000.0) {
            System.out.println("VERDICT: Below market price! Action: BUY.");
        } else {
            System.out.println("VERDICT: Overpriced. Action: SKIP.");
        }
        System.out.println("-----------------------------------");
    }
}
