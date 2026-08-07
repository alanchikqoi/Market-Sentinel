public class Analyzer {
    public String analyzeDeal(Listing target) {
        double pricePerSquare = target.getCost() / target.getArea();

        String str = "Analyzing URL: " + target.getUrl() + "\n" + "Price per SQM: " + String.valueOf(pricePerSquare);

        if (pricePerSquare < 480000.0) {
            str += "\nVERDICT: Below market price! Action: BUY.\n";
        } else {
            str += "\nVERDICT: Overpriced. Action: SKIP.\n";
        }

        return str;
    }
}
