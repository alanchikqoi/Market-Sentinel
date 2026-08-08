public class Analyzer {
    public String analyzeDeal(Listing target) {
        double pricePerSquare = target.getCost() / target.getArea();

        String str = "Analyzing URL: " + target.getUrl() + "\n" +
                "Цена: " + (long) target.getCost() + " 〒\n" +
                "Площадь: " + target.getArea() + " м²\n" +
                "Price per SQM: " + (long) pricePerSquare + " 〒/м²";

        if (pricePerSquare < 480000.0) {
            str += "\n\nVERDICT: Below market price! Action: BUY.";
        } else {
            str += "\n\nVERDICT: Overpriced. Action: SKIP.";
        }

        return str;
    }
}